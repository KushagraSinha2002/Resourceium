#!/usr/bin/env bash

if [ -z "$1" ];
    then PASSWORD="${MYSQL_PASSWORD}"
    else PASSWORD="$1"
fi

if [ -z "$2" ];
    then MAIN_DB="${MYSQL_DATABASE}"
    else MAIN_DB="$2"
fi

if [ -z "$3" ];
    then DB_USER="${MYSQL_USER}"
    else DB_USER="$3"
fi

mysql -u root <<MYSQL_SCRIPT
CREATE DATABASE $MAIN_DB;

CREATE USER '${DB_USER}'@'localhost' IDENTIFIED BY '${PASSWORD}';
GRANT ALL PRIVILEGES ON ${MAIN_DB}.* TO '${DB_USER}'@'localhost';
FLUSH PRIVILEGES;
MYSQL_SCRIPT
