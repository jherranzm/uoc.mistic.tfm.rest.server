#!/bin/bash

USER=jherranzm@gmail.com
USER_NULL=
USER_NOT_EXIST=jherranzm@gmeil.com
USER_NOT_ENABLED=jherranzm@uoc.edu

PASS=123456789Ab!
PASS_INCORRECT=123456789abc
PASS_USER_UOC=12345_67890_
CACERT_PATH=/Users/jherranzm/Downloads/dwcode-xades/cacert.pem

LOGIN_URL=https://127.0.0.1:8443/login

declare -a Test=()
i=0
#
# login, sense usuari i contrasenya
#
#----------------------------------------------------
DATA='{}'
echo 
echo Test[$i]: login, sense usuari i contrasenya, sense dades. Ha de ser rebutjat pel servidor  
echo POST : $DATA

Test[$i]=$(curl -H "Content-Type: application/json" --silent --cacert $CACERT_PATH $LOGIN_URL -X POST -d '{}'  )

echo Test[$i] ${Test[$i]}

#----------------------------------------------------
i=$((i + 1))
DATA='{"op" : ""}'
echo 
echo Test[$i]: login, sense usuari i contrasenya, operació buida. Ha de ser rebutjat pel servidor
echo POST : $DATA

Test[$i]=$(curl -H "Content-Type: application/json" --silent --cacert $CACERT_PATH $LOGIN_URL -X POST -d '{"op" : ""}'  )

echo Test[$i] ${Test[$i]}

#----------------------------------------------------
i=$((i + 1))
DATA='{"op" : "xxx"}'
echo 
echo Test[$i]: login, sense usuari i contrasenya, operació no válida. Ha de ser rebutjat pel servidor
echo POST : $DATA

Test[$i]=$(curl -H "Content-Type: application/json" --silent --cacert $CACERT_PATH $LOGIN_URL -X POST -d '{"op" : "xxx"}' )

echo Test[$i] ${Test[$i]}

#
# login, Amb usuari NO existent
#
#----------------------------------------------------
DATA='{}'
echo 
echo Test[$i]: login, Amb usuari NO existent. Ha de ser rebutjat pel servidor amb error 401
echo POST : $DATA

Test[$i]=$(curl -H "Content-Type: application/json" --silent --cacert $CACERT_PATH $LOGIN_URL -u $USER_NOT_EXIST:$PASS -X POST -d '{}'  )

echo Test[$i] ${Test[$i]}

#----------------------------------------------------
i=$((i + 1))
DATA='{"op" : ""}'
echo 
echo Test[$i]: login, Amb usuari NO existent i contrasenya. Camp operació buida. 
echo POST : $DATA

Test[$i]=$(curl -H "Content-Type: application/json" --silent --cacert $CACERT_PATH $LOGIN_URL -u $USER_NOT_EXIST:$PASS -X POST -d '{"op" : ""}'  )

echo Test[$i] ${Test[$i]}

#----------------------------------------------------
i=$((i + 1))
DATA='{"op" : "xxx"}'
echo 
echo Test[$i]: login, Amb usuari NO existent i contrasenya. Camp operació NO vàlida. 
echo POST : $DATA

Test[$i]=$(curl -H "Content-Type: application/json" --silent --cacert $CACERT_PATH $LOGIN_URL -u $USER_NOT_EXIST:$PASS -X POST -d '{"op" : "xxx"}' )

echo Test[$i] ${Test[$i]}


#
# login, Amb usuari existent pero contrasenya incorrecta. Ha de ser rebutjat pel servidor
#

#----------------------------------------------------
i=$((i + 1))
DATA='{"op" : "login"}'
echo 
echo Test[$i]: login, Amb usuari existent pero contrasenya incorrecta. Ha de ser rebutjat pel servidor 
echo POST : $DATA

Test[$i]=$(curl -H "Content-Type: application/json" --silent --cacert $CACERT_PATH $LOGIN_URL -u $USER:$PASS_INCORRECT -X POST -d '{"op" : "login"}' )

echo Test[$i] ${Test[$i]}

#----------------------------------------------------
i=$((i + 1))
DATA='{"op" : "login"}'
echo 
echo Test[$i]: login, Amb usuari existent, contrasenya correcta. Usuari NO habilitat. Ha de ser rebutjat pel servidor 
echo POST : $DATA

Test[$i]=$(curl -H "Content-Type: application/json" --silent --cacert $CACERT_PATH $LOGIN_URL -u $USER_NOT_ENABLED:$PASS_USER_UOC -X POST -d '{"op" : "login"}' )

echo Test[$i] ${Test[$i]}

#----------------------------------------------------
i=$((i + 1))
DATA='{"op" : "login"}'
echo 
echo Test[$i]: login, Amb usuari existent, contrasenya correcta. Operació correcta. Ha de ser ACCEPTAT pel servidor 
echo POST : $DATA

Test[$i]=$(curl -H "Content-Type: application/json" --silent --cacert $CACERT_PATH $LOGIN_URL -u $USER:$PASS -X POST -d '{"op" : "login"}' )

echo Test[$i] ${Test[$i]}

