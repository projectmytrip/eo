#!/bin/bash

#1 project name
#2 project version
#3 endpoint
#4 namespace
#5 api gw
#6 login public key
#7 gravitee auth token
#8 Skip JWT validation: by default FALSE
#9 application id of rabbit for gravitee
#10 application id of IOC
#11 Skip Rabbit plan and subscription

echo "READ API";

# arguments
ms_name=$1
project_version=$2
deployement_name=$3
namespace=$4
api_gw_url=$5
jwt_public_key=$6
gravitee_auth_token=$7
skip_jwt=$8
rabbit_gravitee_app_id=$9
gravitee_web_app_id=${10}
skip_rabbit=${11}

echo "$api_gw_url"
echo "$skip_jwt"
echo "$rabbit_gravitee_app_id"
echo "$gravitee_web_app_id"
echo "$skip_rabbit"


declare -a curl_common_headers=('-H' "Authorization: Basic $gravitee_auth_token" '-H' "Cache-Control: no-cache" '-H' "Content-Type: application/json;charset=UTF-8")


api_id=$(
  curl -kv -X GET $api_gw_url/management/apis/ \
  "${curl_common_headers[@]}" \
  | jq 'map(select(.name=="'$namespace-$ms_name'") | select( .version=="'$project_version'"))[0].id') ;

echo $api_id;
is_new=false

SUBPNAME="$(echo $ms_name | cut -d '-' -f 2-)"


if [[ "$api_id" = "null" ]] || [[ -z "$api_id" ]]; then
    
    is_new=true
    echo "NESSUNA API TROVATA"
    echo "CREATE API"

    api_id=$(
    curl -kv -X POST $api_gw_url/management/apis/ \
    "${curl_common_headers[@]}" \
    -d '{
    "name": "'$namespace-$ms_name'",
    "version": "'$project_version'",
    "description": "'$namespace-$ms_name'",
    "contextPath": "/'$namespace'/'$ms_name'/'$project_version'",
    "endpoint": "http://'$namespace'-'$SUBPNAME'-'$project_version'-spring-boot.'$namespace'.svc.cluster.local:8080"
    }' | jq -r '.id');

    echo "CREATA API ID:"$api_id;

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
    
    if [[ $skip_jwt == "true" ]]; then
        unrestricted_path_found=true
        whitelist='{"methods":["CONNECT","DELETE","GET","HEAD","OPTIONS","PATCH","POST","PUT","TRACE"],"pattern":"/**"}'
    fi

    
    if [[ $unrestricted_path_found == true ]]; then
        echo "CREATE PLAN FOR UNAUTHENTICATED USER (LIVENESS)"

        plan_id=$(curl -kv -X POST $api_gw_url/management/apis/$api_id/plans \
        "${curl_common_headers[@]}" \
        -d '{
            "name": "'$namespace-$ms_name'-no-auth-plan",
            "description": "Unlimited access plan",
            "validation": "AUTO",
            "security": "KEY_LESS",
            "securityDefinition":"{}",
            "excluded_groups":[],
            "status": "PUBLISHED",
            "paths": {"/":[{"methods": ["GET","POST","PUT","DELETE","HEAD","PATCH","OPTIONS","TRACE","CONNECT"],"enable":true,"resource-filtering":{"whitelist":['"$whitelist"'], "blacklist":[]}}]}}' | jq -r '.id')
        
        echo "PLAN ID: $plan_id"        

    
    fi

    if [[ $skip_jwt != "true" ]]; then
        echo "CREATE PLAN FOR AUTHENTICATED USER"
        auth_plan_id=$(curl -kv -X POST $api_gw_url/management/apis/$api_id/plans \
        "${curl_common_headers[@]}" \
        -d '{
            "name": "'$namespace-$ms_name'-plan",
            "description": "Authenticated user JWT + resource filtering public",
            "validation": "AUTO",
            "security":"jwt",
            "securityDefinition": "{\"signature\":\"RSA_RS256\",\"publicKeyResolver\":\"JWKS_URL\",\"extractClaims\":false,\"propagateAuthHeader\":true,\"resolverParameter\":\"'"$jwt_public_key"'\"}",
            "type": "API",
            "status": "PUBLISHED",
    
            "paths":{"/":
                [
                    {
                      "methods":
                      ["GET","POST","PUT","DELETE","HEAD","PATCH","OPTIONS","TRACE","CONNECT"],
                      "enable":true,
                      "resource-filtering":
                        {
                          "whitelist":[{"methods":["CONNECT","DELETE","GET","HEAD","OPTIONS","PATCH","POST","PUT","TRACE"],"pattern":"/**/public/**"}],
                          "blacklist":[]
                        }
                    }
                ]
            }
        }'  | jq -r '.id')
        
        echo "AUTH PLAN ID: $auth_plan_id"        
        
        echo "CREATE THE WEB SUBSCRIPTION"
        curl -k -X POST "$api_gw_url/management/apis/$api_id/subscriptions?plan=$auth_plan_id&application=$gravitee_web_app_id" \
        "${curl_common_headers[@]}" 
    fi
    

    if [[ $skip_rabbit != "true" ]]; then
        echo "CREATE PLAN FOR A2A Rabbit"
        a2a_plan_id=$(curl -k -X POST $api_gw_url/management/apis/$api_id/plans \
        "${curl_common_headers[@]}" \
        -d '{
                "name": "A2A Rabbit plan",
                "status": "PUBLISHED",
                "description": "A2A Rabbit plan",
                "validation": "auto",
                "characteristics": [],
                "paths": {
                    "/": [
                        {
                        "methods":
                        ["GET","POST","PUT","DELETE","HEAD","PATCH","OPTIONS","TRACE","CONNECT"],
                        "enable":true,
                        "resource-filtering":
                            {
                            "whitelist":[{"methods":["CONNECT","DELETE","GET","HEAD","OPTIONS","PATCH","POST","PUT","TRACE"],"pattern":"/**/rabbit/**"}],
                            "blacklist":[]
                            }
                        }
                    ]
                },
                "security": "api_key",
                "securityDefinition": "{\"propagateApiKey\":false}",
                "excluded_groups": []
            }'  | jq -r '.id')

        echo "AUTH PLAN ID: $a2a_plan_id"


        echo "CREATE THE A2A Rabbit SUBSCRIPTION"
        curl -k -X POST "$api_gw_url/management/apis/$api_id/subscriptions?plan=$a2a_plan_id&application=$rabbit_gravitee_app_id" \
        "${curl_common_headers[@]}" 
    fi
        
else
    api_id=$(echo $api_id | sed -e "s/^\"//" -e "s/\"$//" );
fi





curl -kv -X PUT "$api_gw_url/management/apis/$api_id" \
        "${curl_common_headers[@]}" \
        -d '{"version":"'$project_version'","description":"","proxy":{"context_path":"'$namespace'/'$ms_name'/'$project_version'","strip_context_path":false,"loggingMode":"NONE","endpoints":[{"name":"default","target":"http://'$namespace'-'$SUBPNAME'-'$project_version'-spring-boot.'$namespace'.svc.cluster.local:8080","weight":1,"backup":false,"type":"HTTP","http":{"connectTimeout":5000,"idleTimeout":60000,"keepAlive":true,"readTimeout":1000000,"pipelining":false,"maxConcurrentConnections":100,"useCompression":true,"followRedirects":false}}],"load_balancing":{"type":"ROUND_ROBIN"},"cors":{"enabled":true,"allowCredentials":false,"allowOrigin":["*"],"allowHeaders":["Authorization", "Content-Type", "Pragma", "Cache-Control"],"allowMethods":["DELETE","POST","GET","PUT"],"exposeHeaders":[],"maxAge":-1}},"paths":{"/":[]},"visibility":"private","name":"'$namespace'-'$ms_name'","services":{"discovery":{"enabled":false}},"tags":[],"resources":[],"labels":[]}'


curl -kv -X POST $api_gw_url/management/apis/$api_id/deploy \
        "${curl_common_headers[@]}"
        
if [[ $is_new == true ]]; then
        curl -kv -X POST $api_gw_url/management/apis/$api_id?action=START \
        "${curl_common_headers[@]}"
fi
