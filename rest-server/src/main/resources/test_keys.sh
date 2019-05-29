#!/bin/bash

USER=jherranzm@gmail.com
USER_NOT_EXISTS=jherranzm@gmeil.com

PASS=123456789Ab!
PASS_INCORRECT=123456789Ab!!

KEYS_URL=https://127.0.0.1:8443/keys


CACERT_PATH=/Users/jherranzm/Downloads/dwcode-xades/cacert.pem

declare -a Test=()
i=0
#
# keys, sense usuari ni contrasenya
#
#----------------------------------------------------
i=$((i + 1))
echo 
echo Test[$i]: keys, sense usuari ni contrasenya. Ha de ser rebutjat pel servidor  
echo GET : $KEYS_URL

Test[$i]=$(curl --silent --cacert $CACERT_PATH -X GET $KEYS_URL )

echo Resposta : ${Test[$i]}

#----------------------------------------------------
i=$((i + 1))
echo 
echo Test[$i]: keys, sense usuari ni contrasenya. Ha de ser rebutjat pel servidor  
echo GET : $KEYS_URL/f1

Test[$i]=$(curl -X "DELETE"  --silent --cacert $CACERT_PATH $KEYS_URL/f1 )

echo Resposta : ${Test[$i]}

#----------------------------------------------------
i=$((i + 1))
echo 
echo Test[$i]: keys, sense usuari ni contrasenya. Ha de ser rebutjat pel servidor  
echo GET : $KEYS_URL/f1

Test[$i]=$(curl -X "DELETE"  --silent --cacert $CACERT_PATH $KEYS_URL/f10 )

echo Resposta : ${Test[$i]}

#
# keys, AMB usuari ni contrasenya
#
#----------------------------------------------------
i=$((i + 1))
echo 
echo Test[$i]: keys, AMB usuari ni contrasenya. No hi ha claus  
echo GET : $KEYS_URL

Test[$i]=$(curl --silent --cacert $CACERT_PATH -X GET $KEYS_URL -u $USER:$PASS)

echo Resposta : ${Test[$i]}

#----------------------------------------------------
i=$((i + 1))
echo 
echo Test[$i]: keys, AMB usuari ni contrasenya. No existeix la Clau f1 
echo GET : $KEYS_URL/f1

Test[$i]=$(curl -X "DELETE"  --silent --cacert $CACERT_PATH $KEYS_URL/f1 -u $USER:$PASS )

echo Resposta : ${Test[$i]}

#----------------------------------------------------
i=$((i + 1))
echo 
echo Test[$i]: keys, AMB usuari ni contrasenya. No existeix la clau f10  
echo GET : $KEYS_URL/f1

Test[$i]=$(curl -X "DELETE"  --silent --cacert $CACERT_PATH $KEYS_URL/f10 -u $USER:$PASS )

echo Resposta : ${Test[$i]}
