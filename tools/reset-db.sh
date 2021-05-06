#!/usr/bin/env bash

APP_DB_USER="admin-user"
APP_DB_NAME="website-db"

cat << EOF | sudo su - postgres -c psql
-- Delete the database if it exists
DROP DATABASE if exists "$APP_DB_NAME";
-- Create the database:
CREATE DATABASE "$APP_DB_NAME" WITH OWNER="$APP_DB_USER"
                                  LC_COLLATE="en_US.utf8"
                                  LC_CTYPE="en_US.utf8"
                                  ENCODING="UTF8"
                                  TEMPLATE=template0;
-- Grant necessary privileges
GRANT ALL PRIVILEGES ON DATABASE "$APP_DB_NAME" TO "$APP_DB_USER";
EOF

DB_USER="username"
MAIN_DB="server_db"

cat << EOF | sudo su - postgres -c psql
-- Delete the database if it exists
DROP DATABASE if exists "$MAIN_DB";
-- Create the database:
CREATE DATABASE "$MAIN_DB" WITH OWNER="$DB_USER"
                                  LC_COLLATE="en_US.utf8"
                                  LC_CTYPE="en_US.utf8"
                                  ENCODING="UTF8"
                                  TEMPLATE=template0;
-- Grant necessary privileges
GRANT ALL PRIVILEGES ON DATABASE "$MAIN_DB" TO "$DB_USER";
EOF

rm -rf storage/media
