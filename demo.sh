#!/bin/bash


GREEN='\033[0:32m'
NC='\033[0m'

arg1_1="my&friend&Paul has heavy hats! &"
arg1_2="my friend John has many many friends &"

echo -e "Testing assignment 1 ..."
echo -e "---> arg 1 = \"$arg1_1\""
echo -e "---> arg 2 = \"$arg1_2\""
echo -e "---> mix(arg1,arg2) --> ${GREEN}" $(java -jar assignment1/target/assignment1-1.0.0-SNAPSHOT.jar "${arg1_1}" "${arg1_2}")
echo -e "${NC}==================================================================="

arg2_1="Where are you?"
arg2_2="You should be coding!!?"
arg2_3="c'amon baby, you should see it;"
arg2_4="several errors today"

echo -e "Testing assingment 2 ..."
echo -e "---> s1 = \"$arg2_1\""
echo -e "---> s2 = \"$arg2_2\""
echo -e "---> s3 = \"$arg2_3\""
echo -e "---> s4 = \"$arg2_4\""
echo -e "---> mix(s1,s2,s3,s4) --> ${GREEN}" $(java -jar assignment2/target/assignment2-1.0.0-SNAPSHOT.jar "${arg2_1}" "${arg2_2}" "${arg2_3}" "${arg2_4}")
echo -e "${NC}==================================================================="

echo -e "Testing assignment 3... "
echo -e "Server will be running background..."
exec java -jar assignment3/target/assignment3-1.0.0-SNAPSHOT.jar > /dev/null &

PID=$!
echo -e "wait until pid: $PID returns ok at healthcheck"
URI="http://localhost:8181/health"
API="http://localhost:8181/api/mix"

start_ts=$(date +%s)
while :
do
    result=$(curl -s -o /dev/null -w "%{http_code}" -kI $URI)
    if [[ $result -eq 200 ]]; then
        end_ts=$(date +%s)
        echo -e "rest api server is now available after $((end_ts - start_ts)) seconds"
        break
    fi
    sleep 1
done

echo -e "curl execution --> ${GREEN}" $(curl "$API" -sX POST -H 'Content-Type: application/json;charset=UTF-8' -d '{"strings":["Where are you?","You should be coding!!?","camon baby, you should see it;","several errors today"]}')
echo -e "${NC}==================================================================="
echo
kill -9 $PID
echo -e "process killed.. end of demo"



