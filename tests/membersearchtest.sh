curl -i \
    -H "Accept: application/json" \
    -H "Content-Type: application/json" \
    -X POST -d @./membersearch.json \
    http://localhost:9080/teetimepwcc/v1/member/search
    
