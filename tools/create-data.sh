#!/usr/bin/env bash

BASE_URL="http://localhost:8000"
TOTAL=10
for i in $(seq 1 $TOTAL);
do
    USERNAME="$(tr -dc A-Za-z0-9 </dev/urandom | head -c 20 ; echo '')"
    EMAIL="$(tr -dc A-Za-z0-9 </dev/urandom | head -c 15 ; echo '')@email.com"
    PASSWORD="$(tr -dc A-Za-z0-9 </dev/urandom | head -c 15 ; echo '')@email.com"
    BIO="$(tr -dc A-Za-z0-9 </dev/urandom | head -c 100 ; echo '')"

    curl -X POST "${BASE_URL}/accounts/register/" -d "name=${USERNAME}&email=${EMAIL}&password=${PASSWORD}"
    echo
done

for i in $(seq 1 $TOTAL);
do
    RANDOM_USER="$(shuf -i 1-$TOTAL -n 1)"

    CONTENTS="$(tr -dc A-Za-z0-9 </dev/urandom | head -c 10000 ; echo '')"
    FILE_1="/tmp/$(tr -dc A-Za-z0-9 </dev/urandom | head -c 10 ; echo '').txt"
    echo "${CONTENTS}" >> "$FILE_1"
    curl -s -i -X POST -H "Content-Type: multipart/form-data" "${BASE_URL}/uploads/upload/${RANDOM_USER}" -F "files=@$FILE_1" -o /dev/null
done
echo "Uploaded ${TOTAL} files!"
