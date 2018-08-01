curl -i \
    -H "Accept: application/json" \
    -H "Content-Type: application/json" \
    -X POST -d @./membersearch.json \
    https://teetime-pwcc.mybluemix.net/teetimepwcc/v1/member/search
