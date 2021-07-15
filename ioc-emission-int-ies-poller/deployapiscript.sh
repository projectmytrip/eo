#!/bin/bash

#1 project name
#2 project version
#3 endpoint
#4 namespace
#5 api gw

echo "READ API";

API_ID=$(
  curl -X GET \
  $5/management/apis/ \
  -H 'Authorization: Basic YWRtaW46YWRtaW4=' \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json;charset=UTF-8'\
  | jq 'map(select(.name=="'$4-$1'") | select( .version=="'$2'"))[0].id') ;

echo $API_ID;

if [ $API_ID = null ]; then
        echo "NESSUNA API TROVATA";
        echo "CREATE API"
        
        SUBPNAME="$(echo $1 | cut -d '-' -f 2-)"


        

        API_ID=$(
        curl -X POST \
        $5/management/apis/ \
        -H 'Authorization: Basic YWRtaW46YWRtaW4=' \
        -H 'Cache-Control: no-cache' \
        -H 'Content-Type: application/json;charset=UTF-8' \
        -d '{
        "name": "'$4-$1'",
        "version": "'$2'",
        "description": "",
        "contextPath": "'$4'/'$1'/'$2'",
        "endpoint": "http://'$4'-'$SUBPNAME'-'$2'-spring-boot.'$4'.svc.cluster.local:8080"
        }'\
        -k\
        | jq '.id'\
        | sed -e "s/^\"//" -e "s/\"$//");

        echo "CREATA API ID:"$API_ID;


        PLAN_ID=$(
        curl -X POST \
        $5/management/apis/$API_ID/plans \
        -H 'Authorization: Basic YWRtaW46YWRtaW4=' \
        -H 'Cache-Control: no-cache' \
        -H 'Content-Type: application/json;charset=UTF-8' \
        -d '{
        "name": "'$4-$1'-plan",
        "description": "Unlimited access plan",
        "validation": "AUTO",
        "security": "KEY_LESS",
        "type": "API",
        "status": "PUBLISHED",
        "paths": {}
        }'\
        -k\
        | jq '.id'\
        | sed -e "s/^\"//" -e "s/\"$//");

        
        echo "Settings for CORS"
        curl $5/management/apis/$API_ID -X PUT -H 'Authorization: Basic YWRtaW46YWRtaW4=' -H 'Content-Type: application/json;charset=UTF-8' -H 'Cache-Control: no-cache' -d '{"version":"'$2'","description":"","proxy":{"context_path":"'$4'/'$1'/'$2'","strip_context_path":false,"loggingMode":"NONE","endpoints":[{"name":"default","target":"http://'$4'-'$SUBPNAME'-'$2'-spring-boot.'$4'.svc.cluster.local:8080","weight":1,"backup":false,"type":"HTTP","http":{"connectTimeout":5000,"idleTimeout":60000,"keepAlive":true,"readTimeout":100000,"pipelining":false,"maxConcurrentConnections":100,"useCompression":true,"followRedirects":false}}],"load_balancing":{"type":"ROUND_ROBIN"},"cors":{"enabled":true,"allowCredentials":false,"allowOrigin":["*"],"allowHeaders":["Authorization", "Content-Type"],"allowMethods":["DELETE","POST","GET","PUT"],"exposeHeaders":[],"maxAge":-1}},"paths":{"/":[]},"visibility":"private","name":"'$4-$1'","services":{"discovery":{"enabled":false}},"tags":[],"resources":[],"labels":[]}'

        
        curl -H "Authorization: Basic YWRtaW46YWRtaW4=" \
        -X POST \
        -k\
        $5/management/apis/$API_ID/deploy

        curl -H "Authorization: Basic YWRtaW46YWRtaW4=" \
        -X POST \
        -k\
        $5/management/apis/$API_ID?action=START

fi