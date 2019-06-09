#!/bin/bash

USER=jherranzm@gmail.com
PASS=123456789Ab!
CACERT_PATH=/Users/jherranzm/Downloads/dwcode-xades/cacert.pem

FACTURAS_URL=https://127.0.0.1:8443/facturas
SIGNUP_URL=https://127.0.0.1:8443/signup
LOGIN_URL=https://127.0.0.1:8443/login
KEYS_URL=https://127.0.0.1:8443/keys

USER_NOT_EXISTS=jherranzm@gmeil.com

declare -a Test=()
i=0
#----------------------------------------------------
DATA='{}'
echo 
echo Test[$i]: sign up, sense dades 
echo POST : $DATA

Test[$i]=$(curl -H "Content-Type: application/json" -X POST -d $DATA --silent --cacert $CACERT_PATH $SIGNUP_URL )

echo Test[$i] ${Test[$i]}

#----------------------------------------------------
i=$((i + 1))
DATA='{"op" : ""}'
echo 
echo Test[$i]: sign up, camp operació buida 
echo POST : $DATA

Test[$i]=$(curl -H "Content-Type: application/json" -X POST -d '{"op" : ""}' --silent --cacert $CACERT_PATH $SIGNUP_URL )

echo Test[$i] ${Test[$i]}

#----------------------------------------------------
i=$((i + 1))
DATA='{"op" : "xxx"}'
echo 
echo Test[$i]: sign up, camp operació NO vàlida 
echo POST : $DATA

Test[$i]=$(curl -H "Content-Type: application/json" -X POST -d '{"op" : "xxx"}' --silent --cacert $CACERT_PATH $SIGNUP_URL )

echo Test[$i] ${Test[$i]}

#----------------------------------------------------
i=$((i + 1))
DATA='{"op" : "signup"}'
echo 
echo Test[$i]: sign up, camp operació correcta però falten camps 
echo POST : $DATA

Test[$i]=$(curl -H "Content-Type: application/json" -X POST -d '{"op" : "signup"}' --silent --cacert $CACERT_PATH $SIGNUP_URL )

echo Test[$i] ${Test[$i]}

#----------------------------------------------------
i=$((i + 1))
DATA='{"op" : "signup", "username":""}'
echo 
echo Test[$i]: sign up, camp operació correcta però l''usuari està buit 
echo POST : $DATA


Test[$i]=$(curl -H "Content-Type: application/json" -X POST -d '{"op" : "signup", "username":""}' --silent --cacert $CACERT_PATH $SIGNUP_URL )

echo Test[$i] ${Test[$i]}

#----------------------------------------------------
i=$((i + 1))
DATA='{"op" : "signup", "username":"xxx@@@"}'
echo 
echo Test[$i]: sign up, camp operació correcta però l''usuari no és un email formalment correcte 
echo POST : $DATA

Test[$i]=$(curl -H "Content-Type: application/json" -X POST -d '{"op" : "signup", "username":"xxx@@@"}' --silent --cacert $CACERT_PATH $SIGNUP_URL )

echo Test[$i] ${Test[$i]}

#----------------------------------------------------
i=$((i + 1))
DATA='{"op" : "signup", "username":"jherranzm@uoc.edu"}'
echo 
echo Test[$i]: sign up, camp operació correcta. Usuari correcte però no té el camp password 
echo POST : $DATA

Test[$i]=$(curl -H "Content-Type: application/json" -X POST -d '{"op" : "signup", "username":"jherranzm@uoc.edu"}' --silent --cacert $CACERT_PATH $SIGNUP_URL )

echo Test[$i] ${Test[$i]}

#----------------------------------------------------
i=$((i + 1))
DATA='{"op" : "signup", "username":"jherranzm@gmail.com", "pass" : ""}'
echo 
echo Test[$i]: sign up, camp operació correcta. Usuari correcte però el camp password està buit
echo POST : $DATA

Test[$i]=$(curl -H "Content-Type: application/json" -X POST -d '{"op" : "signup", "username":"jherranzm@gmail.com", "pass" : ""}' --silent --cacert $CACERT_PATH $SIGNUP_URL )

echo Test[$i] ${Test[$i]}

#----------------------------------------------------
i=$((i + 1))
DATA='{"op" : "signup", "username":"jherranzm@gmail.com", "pass" : "123456"}'
echo 
echo Test[$i]: sign up, camp operació correcta. Usuari correcte però el camp password no té la llargada adequada
echo POST : $DATA

Test[$i]=$(curl -H "Content-Type: application/json" -X POST -d '{"op" : "signup", "username":"jherranzm@gmail.com", "pass" : "123456"}' --silent --cacert $CACERT_PATH $SIGNUP_URL )

echo Test[$i] ${Test[$i]}

#----------------------------------------------------
i=$((i + 1))
DATA='{"op" : "signup", "username":"jherranzm@gmail.com", "pass":"xxx-xxx-xxx-"}'
echo 
echo Test[$i]: sign up, camp operació correcta. Usuari correcte però ja està al sistema 
echo POST : $DATA

Test[$i]=$(curl -H "Content-Type: application/json" -X POST -d '{"op" : "signup", "username":"jherranzm@gmail.com", "pass":"xxx-xxx-xxx-"}' --silent --cacert $CACERT_PATH $SIGNUP_URL )

echo Test[$i] ${Test[$i]}

#----------------------------------------------------
i=$((i + 1))
DATA='{"op" : "signup", "username":"jherranzm@uoc.edu", "pass" : "12345_67890_"}'
echo 
echo Test[$i]: sign up, camp operació correcta. Usuari correcte i password de llargada correcta. 
echo POST : $DATA

Test[$i]=$(curl -H "Content-Type: application/json" -X POST -d '{"op" : "signup", "username":"jherranzm@uoc.edu", "pass" : "12345_67890_"}' --silent --cacert $CACERT_PATH $SIGNUP_URL )

echo Test[$i] ${Test[$i]}

# echo 
# echo Test: sign up
# echo 
# 
# TEST=$(curl -H "Content-Type: application/json" -X POST -d '{"op":"signup", "username":"jherranzm.dev@gmail.com","pass":"123123123abc"}' --silent --cacert $CACERT_PATH $SIGNUP_URL )
# 
# echo $TEST
