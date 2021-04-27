#!/usr/bin/env bash

PASSWORD="${PASSWORD}"

MAIN_DB="${DATABASE_NAME}"

DB_USER="${DATABASE_USER}"

mysql -u root <<MYSQL_SCRIPT
CREATE DATABASE $MAIN_DB;

CREATE DATABASE ${MAIN_DB} /*\!40100 DEFAULT CHARACTER SET utf8 */;
CREATE USER '${DB_USER}'@'localhost' IDENTIFIED BY '${PASSWORD}';
GRANT ALL PRIVILEGES ON ${MAIN_DB}.* TO '${DB_USER}'@'localhost';
FLUSH PRIVILEGES;
MYSQL_SCRIPT
