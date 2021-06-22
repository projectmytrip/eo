#!/bin/bash
#1 project name
#2 project version
#3 endpoint
#4 namespace
#5 api gw
#6 login public key
#7 gravitee auth token
#8 gravitee web app id
#9 gravitee mobile app id

# arguments
ms_name=$1
project_version=$2
deployement_name=$3
namespace=$4
api_gw_url=$5
jwt_public_key=$6
gravitee_auth_token=$7
gravitee_web_app_id=$8
gravitee_mobile_app_id=$9


declare -a curl_common_headers=('-H' "Authorization: Basic $gravitee_auth_token" '-H' "Cache-Control: no-cache" '-H' "Content-Type: application/json;charset=UTF-8")


echo "CHECK IF THE API EXISTS";

api_id=$(
    curl -k -v -X GET "$api_gw_url/management/apis/" \
    "${curl_common_headers[@]}" \
| jq -r 'map(select(.name=="'$namespace-$ms_name'") | select( .version=="'$project_version'"))[0].id') ;

echo $api_id;
is_new=false

if [[  $api_id == null ]] || [[ -z  $api_id ]]; then
    is_new=true
    echo "THE API DOES NOT EXIST"
    echo "CREATE A NEW API"
    
    echo "CHECK IF THE GRAVITEE WEB APP ${gravitee_web_app_id} EXISTS"
    check_web_app_id=$(curl -k "$api_gw_url/management/applications/$gravitee_web_app_id" "${curl_common_headers[@]}" | jq -r '.id')
    echo "CHECK THE WEB APP ID: $check_web_app_id"
    if [[ $check_web_app_id == null ]] || [[ -z  $check_web_app_id ]]; then
        echo "ERROR: The web app $gravitee_web_app_id was not found. Please provide a valid web app id" 1>&2
        exit 1
    fi
    
    echo "CHECK IF THE GRAVITEE MOBILE APP ${gravitee_mobile_app_id} EXISTS"
    check_mobile_app_id=$(curl -k "$api_gw_url/management/applications/$gravitee_mobile_app_id" "${curl_common_headers[@]}" | jq -r '.id')
    echo "CHECK THE MOBILE APP ID: $check_mobile_app_id"
    if [[ $check_mobile_app_id == null ]] || [[ -z  $check_mobile_app_id ]]; then
        echo "ERROR: The mobile app $gravitee_mobile_app_id was not found. Please provide a valid mobile app id" 1>&2
        exit 1
    fi
    
    api_id=$(
        curl -k -X POST $api_gw_url/management/apis/ \
        "${curl_common_headers[@]}" \
    -d '{"name": "'$namespace-$ms_name'", "version": "'$project_version'", "description": "", "contextPath": "'$namespace'/'$ms_name'/'$project_version'","endpoint": "http://'$deployement_name'-'$project_version'-spring-boot.'$namespace'.svc.cluster.local:8080"}' | jq -r '.id');
    
    echo "THE NEW API ID: "$api_id;
    
    if [[ $api_id == null ]] || [[ -z  $api_id ]]; then
        echo "ERROR: An error occurred while creating the new API" 1>&2
        exit 1
    fi
    
    echo "CREATE A PLAN FOR AUTHENTICATED USER"
    auth_plan_id=$(curl -k -X POST $api_gw_url/management/apis/$api_id/plans \
        "${curl_common_headers[@]}" \
        -d '{
          "name": "'$namespace-$ms_name'-auth-plan",
          "status": "PUBLISHED",
          "description":"Authenticated users only plan",
          "validation":"auto",
          "characteristics":[],
          "paths":{"/":[]},
          "security":"jwt",
          "securityDefinition" : "{\"signature\":\"RSA_RS256\",\"publicKeyResolver\":\"JWKS_URL\",\"extractClaims\":false,\"propagateAuthHeader\":true,\"resolverParameter\":\"'"$jwt_public_key"'\"}",
          "excluded_groups":[]
    }'  | jq -r '.id')
    
    echo "AUTH PLAN ID: $auth_plan_id"
    
    
    echo "CREATE THE WEB SUBSCRIPTION"
    curl -k -X POST "$api_gw_url/management/apis/$api_id/subscriptions?plan=$auth_plan_id&application=$gravitee_web_app_id" \
    "${curl_common_headers[@]}"
    
    echo "CREATE THE MOBILE SUBSCRIPTION"
    curl -k -X POST "$api_gw_url/management/apis/$api_id/subscriptions?plan=$auth_plan_id&application=$gravitee_mobile_app_id" \
    "${curl_common_headers[@]}"
    
    
    whitelist=''
    unrestricted_path_found=false
    if [[ -f unauthenticated_endpoints.txt ]]; then
        while read upath || [[ -n "$upath" ]]; do
            if [[ ! $upath == \#* && ! -z $upath ]]; then
                unrestricted_path='{
                    "methods":  ["CONNECT","DELETE","GET","HEAD","OPTIONS","PATCH","POST","PUT","TRACE"],
                    "pattern": "/**/'"$upath"'/**"
                }'
                [[ $unrestricted_path_found == false ]] && whitelist=$unrestricted_path || whitelist=$whitelist','$unrestricted_path;
                unrestricted_path_found=true
            fi
        done <unauthenticated_endpoints.txt
    fi
    if [[ $unrestricted_path_found == true ]]; then
        curl -k -X POST $api_gw_url/management/apis/$api_id/plans \
        "${curl_common_headers[@]}" \
        -d '{
              "name":"'$namespace-$ms_name'-no-auth-plan",
              "status": "PUBLISHED",
              "description":"Unauthenticated user plan",
              "validation":"auto",
              "characteristics":[],
              "paths":
              {
                "/":
                  [
                    {
                      "methods":
                      ["GET","POST","PUT","DELETE","HEAD","PATCH","OPTIONS","TRACE","CONNECT"],
                      "enable":true,
                      "resource-filtering":
                        {
                          "whitelist":['"$whitelist"'],
                          "blacklist":[]
                        }
                      }
                  ]
                },
                "security":"key_less",
                "securityDefinition":"{}",
                "excluded_groups":[]
        }'
    fi
fi

echo "SETUP THE CORS"

curl -k -X PUT "$api_gw_url/management/apis/$api_id" \
"${curl_common_headers[@]}" \
-d '{"version":"'$project_version'","description":"","proxy":{"context_path":"'$namespace'/'$ms_name'/'$project_version'","strip_context_path":false,"loggingMode":"NONE","endpoints":[{"name":"default","target":"http://'$deployement_name'-'$project_version'-spring-boot.'$namespace'.svc.cluster.local:8080","weight":1,"backup":false,"type":"HTTP","http":{"connectTimeout":5000,"idleTimeout":60000,"keepAlive":true,"readTimeout":3000000,"pipelining":false,"maxConcurrentConnections":100,"useCompression":true,"followRedirects":false}}],"load_balancing":{"type":"ROUND_ROBIN"},"cors":{"enabled":true,"allowCredentials":false,"allowOrigin":["*"],"allowHeaders":["Authorization", "Content-Type", "Pragma", "Cache-Control"],"allowMethods":["DELETE","POST","GET","PUT"],"exposeHeaders":[],"maxAge":-1}},"paths":{"/":[]},"visibility":"private","name":"'$namespace'-'$ms_name'","services":{"discovery":{"enabled":false}},"tags":[],"resources":[],"labels":[]}'



curl -k -X POST $api_gw_url/management/apis/$api_id/deploy \
"${curl_common_headers[@]}"

if [[ $is_new == true ]]; then
    curl -k -X POST $api_gw_url/management/apis/$api_id?action=START \
    "${curl_common_headers[@]}"
fi
