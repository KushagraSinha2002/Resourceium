#!/usr/bin/env bash

PASSWORD="${1:-$DB_PASSWORD}"

MAIN_DB="${2:-$DB_DATABASE}"

DB_USER="${3:-$DB_USER}"

ROOT_USER="${4:-gitpod}"

printr "Creating server database"

psql -U "${ROOT_USER}" << SQL_COMMANDS
-- Database for the server->
-- Create the database user:
CREATE ROLE "$DB_USER" WITH LOGIN CREATEDB ENCRYPTED PASSWORD '$PASSWORD';
-- Create the database:
CREATE DATABASE "$MAIN_DB" WITH OWNER="$DB_USER"
                                  LC_COLLATE="en_US.utf8"
                                  LC_CTYPE="en_US.utf8"
                                  ENCODING="UTF8"
                                  TEMPLATE=template0;
-- Grant necessary privileges
GRANT ALL PRIVILEGES ON DATABASE "$MAIN_DB" TO "$DB_USER";
SQL_COMMANDS

printr -c green -f bold "Successfully created server database!"
echo
printr "Creating storage database"

DJANGO_DATABASE_USER="admin-user"
DJANGO_DATABASE_PASSWORD="admin-password"
DJANGO_DATABASE_NAME="website-db"

psql -U "${ROOT_USER}" << SQL_COMMANDS
-- Database for the storage server->
CREATE ROLE "$DJANGO_DATABASE_USER" WITH LOGIN CREATEDB ENCRYPTED PASSWORD '$DJANGO_DATABASE_PASSWORD';
-- Create the database:
CREATE DATABASE "$DJANGO_DATABASE_NAME" WITH OWNER="$DJANGO_DATABASE_USER"
                                  LC_COLLATE="en_US.utf8"
                                  LC_CTYPE="en_US.utf8"
                                  ENCODING="UTF8"
                                  TEMPLATE=template0;
-- Grant necessary privileges
GRANT ALL PRIVILEGES ON DATABASE "$DJANGO_DATABASE_NAME" TO "$DJANGO_DATABASE_USER";
SQL_COMMANDS

printr -c green -f bold "Successfully created storage database!"
