curl -i \
    -H "Accept: application/json" \
    -H "Content-Type: application/json" \
    -X POST -d @./teetime.json \
    https://teetime-pwcc.mybluemix.net/teetimepwcc/v1/teetime/search    
    
#    http://localhost:9080/teetimepwcc/v1/search