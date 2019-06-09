#!/bin/bash

CACERT_PATH=/Users/jherranzm/Downloads/dwcode-xades/cacert.pem

TOKEN_URL=https://127.0.0.1:8443/token

declare -a Test=()
i=0
#
# token, sense token
#
#----------------------------------------------------
i=$((i + 1))
echo 
echo Test[$i]: token, sense token. Ha de ser rebutjat pel servidor  
echo GET : $TOKEN_URL

Test[$i]=$(curl --silent --cacert $CACERT_PATH -X GET $TOKEN_URL )

echo Resposta : ${Test[$i]}


#
# token, token erroni o no existent
#
#----------------------------------------------------
i=$((i + 1))
echo 
echo Test[$i]: token, token erroni o no existent. Ha de ser rebutjat pel servidor  
echo GET : $TOKEN_URL/pepe

Test[$i]=$(curl --silent --cacert $CACERT_PATH -X GET $TOKEN_URL/pepe )

echo Resposta : ${Test[$i]}

#
# token, token vàlid
#
#----------------------------------------------------
i=$((i + 1))
echo 
echo Test[$i]: token, token vàlid. Ha de ser ACCEPTAT pel servidor  
echo GET $TOKEN_URL/2yyKpy5lzMmrFmr40cGyqpVCZdMdvo0FSP7oG317m1Vdj7Om1bDnHkObHo3foM2o8LIolHzGiJDeKGFNUWQOtWOb9rVNXgcN4zSGBZG5Ipb1vNeZtHUwDcD4qYFaowwnXGPGdyOU8hpIB0gIYe2XxPk12SDA4ux47l1s9Th9Idvr0mWU2Cz1CrByH9AVhJ3TCp3l0QFPDwYI3jSZDvPJh2q4KvrSzCn6OZsrNPNdiFvHabnKNNmF1CwSU3i5aje2

Test[$i]=$(curl --silent --cacert $CACERT_PATH -X GET $TOKEN_URL/2yyKpy5lzMmrFmr40cGyqpVCZdMdvo0FSP7oG317m1Vdj7Om1bDnHkObHo3foM2o8LIolHzGiJDeKGFNUWQOtWOb9rVNXgcN4zSGBZG5Ipb1vNeZtHUwDcD4qYFaowwnXGPGdyOU8hpIB0gIYe2XxPk12SDA4ux47l1s9Th9Idvr0mWU2Cz1CrByH9AVhJ3TCp3l0QFPDwYI3jSZDvPJh2q4KvrSzCn6OZsrNPNdiFvHabnKNNmF1CwSU3i5aje2  )
echo Resposta : ${Test[$i]}

#https://localhost:8443/token/2yyKpy5lzMmrFmr40cGyqpVCZdMdvo0FSP7oG317m1Vdj7Om1bDnHkObHo3foM2o8LIolHzGiJDeKGFNUWQOtWOb9rVNXgcN4zSGBZG5Ipb1vNeZtHUwDcD4qYFaowwnXGPGdyOU8hpIB0gIYe2XxPk12SDA4ux47l1s9Th9Idvr0mWU2Cz1CrByH9AVhJ3TCp3l0QFPDwYI3jSZDvPJh2q4KvrSzCn6OZsrNPNdiFvHabnKNNmF1CwSU3i5aje2

