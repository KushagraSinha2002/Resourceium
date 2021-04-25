#!/usr/bin/env bash

TOTAL=10
for i in $(seq 1 $TOTAL);
do
    USERNAME="$(tr -dc A-Za-z0-9 </dev/urandom | head -c 20 ; echo '')"
    EMAIL="$(tr -dc A-Za-z0-9 </dev/urandom | head -c 15 ; echo '')@email.com"
    BIO="$(tr -dc A-Za-z0-9 </dev/urandom | head -c 100 ; echo '')"

    curl -X POST "http://localhost:8000/accounts/new/" -d "name=${USERNAME}&email=${EMAIL}&bio=${BIO}"
    echo
done

for i in $(seq 1 $TOTAL);
do
    RANDOM_USER="$(shuf -i 1-$TOTAL -n 1)"

    CONTENTS="$(tr -dc A-Za-z0-9 </dev/urandom | head -c 10000 ; echo '')"
    echo "${CONTENTS}" >> /tmp/file.txt
    curl -sSL -i -X POST -H "Content-Type: multipart/form-data" "http://localhost:8000/files/upload/${RANDOM_USER}" -F "file=@/tmp/file.txt"
    rm /tmp/file.txt
done
