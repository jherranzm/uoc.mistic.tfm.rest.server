#!/bin/bash

USER=jherranzm@gmail.com
PASS=123456789Ab!
CACERT_PATH=/Users/jherranzm/Downloads/dwcode-xades/cacert.pem

FACTURAS_URL=https://127.0.0.1:8443/facturas
SIGNUP_URL=https://127.0.0.1:8443/signup
LOGIN_URL=https://127.0.0.1:8443/login
KEYS_URL=https://127.0.0.1:8443/keys

USER_NOT_EXISTS=jherranzm@gmeil.com

echo 
echo Test 001: user not exists
echo 

TEST_001=$(curl --silent --cacert $CACERT_PATH $FACTURAS_URL -u $USER_NOT_EXISTS:$PASS)

echo $TEST_001

echo 
echo Test 002: user:user
echo 

TEST_001=$(curl --silent --cacert $CACERT_PATH $FACTURAS_URL -u user:user)

echo $TEST_001

echo 
echo Test 003: admin:admin
echo 

TEST_003=$(curl --silent --cacert $CACERT_PATH $FACTURAS_URL -u admin:admin)

echo $TEST_003


echo 
echo Test 004: user and password correct!
echo 

TEST_004=$(curl --silent --cacert $CACERT_PATH $FACTURAS_URL -u $USER:$PASS)

echo $TEST_004

echo 
echo Test 005: sign up
echo 

TEST_005=$(curl -H "Content-Type: application/json" -X POST -d '{"op":"signup", "username":"jherranzm.dev@gmail.com","pass":"123123123abc"}' --silent --cacert $CACERT_PATH $SIGNUP_URL )

echo $TEST_005

echo 
echo Test 006: log in user NOT enabled
echo 

TEST_006=$(curl -H "Content-Type: application/json" -X POST -d '{"op":"login", "username":"jherranzm.dev@gmail.com","pass":"123123123abc"}' --silent --cacert $CACERT_PATH $LOGIN_URL -u jherranzm.dev@gmail.com:123123123abc )

echo $TEST_006


echo 
echo Test 007: log in with incorrect password
echo 

TEST_007=$(curl -H "Content-Type: application/json" -X POST -d '{"op":"login"}' --silent --cacert $CACERT_PATH $LOGIN_URL -u jherranzm.dev@gmail.com:123123123abb )

echo $TEST_007



echo 
echo Test 008: log in with incorrect password
echo 

TEST_008=$(curl -H "Content-Type: application/json" -X POST -d '{"op":"login"}' --silent --cacert $CACERT_PATH $LOGIN_URL -u $USER:$PASS )

echo $TEST_008

echo 
echo Test 009: delete existing sym key backed up in server
echo 

TEST_009=$(curl -X "DELETE"  --silent --cacert $CACERT_PATH https://127.0.0.1:8443/keys/f1 -u $USER:$PASS )

echo $TEST_009


echo 
echo Test 010: delete non existing sym key backed up in server
echo 

TEST_010=$(curl -X "DELETE"  --silent --cacert $CACERT_PATH https://127.0.0.1:8443/keys/f10 -u $USER:$PASS )

echo $TEST_010



echo 
echo Test 011: delete all existing sym key backep up in server 
echo 

TEST_011=$(curl -X "DELETE"  --silent --cacert $CACERT_PATH https://127.0.0.1:8443/keys -u $USER:$PASS )

echo $TEST_011

