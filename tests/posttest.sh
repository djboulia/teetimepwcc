curl -i \
    -H "Accept: application/json" \
    -H "Content-Type: application/json" \
    -X POST -d @./teetime.json \
    http://localhost:9080/teetimepwcc/v1/teetime/search
    
