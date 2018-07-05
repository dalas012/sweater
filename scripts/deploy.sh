#!/usr/bin/env bash

mvn clean package

echo 'Copy files...'

scp -i ~/.ssh/sweaterkey \
    ./target/sweater-1.0-SNAPSHOT.jar \
    dalas@sweater.website:/home/dalas/

echo 'Restart server...'

ssh -i ~/.ssh/sweaterkey dalas@sweater.website << EOF
pgrep java | xargs kill -9
nohup java -jar sweater-1.0-SNAPSHOT.jar > log.txt &
EOF

echo 'Bye!'
