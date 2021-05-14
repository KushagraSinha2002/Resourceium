alias t := test-e2e
alias h := test-e2e-headless

export PIP_USER := "false"

backend_folder := "server"
frontend_folder := "client"
storage_folder := "storage"

# Run the servers in development mode.
default: setup
	#!/usr/bin/env bash
	if command -v gp &>/dev/null;
		then URL="$(gp url 8000)"
		else URL="http://localhost:8000/"
	fi
	{ cd "{{storage_folder}}" && poetry run python manage.py runserver 0.0.0.0:8080 & \
	cd "{{backend_folder}}" && mvn spring-boot:run -Dspring-boot.run.profiles=dev & \
	cd "{{frontend_folder}}" && API_URL="${URL}" yarn dev; }

# Setup the dependencies for all the microservices.
setup:
	#!/usr/bin/env bash
	cd "{{frontend_folder}}" && yarn install
	cd "../{{storage_folder}}" && poetry install && poetry run python manage.py migrate

# Build the project for integration testing.
build: setup
	#!/usr/bin/env bash
	cd "{{frontend_folder}}"
	yarn build

# Run the servers that will be used for the integration tests.
run-servers: build
	#!/usr/bin/env bash
	cd "{{frontend_folder}}"
	pm2 start "yarn start"
	wait-on -v http-get://127.0.0.1:3000/

	cd "../{{storage_folder}}"
	pm2 start "poetry run gunicorn storage.wsgi:application -b 127.0.0.1:8080"
	wait-on -v http-get://127.0.0.1:8080/

	cd "../{{backend_folder}}"
	pm2 start "mvn spring-boot:run -Dspring-boot.run.profiles=dev"
	wait-on -v http-get://127.0.0.1:8000/accounts/all

# Kill the servers that were spawned by the `run-servers` recipe.
kill-servers:
	#!/usr/bin/env bash
	pm2 kill

# Execute all the integration tests in interactive mode.
test-e2e: run-servers
	#!/usr/bin/env bash
	cd {{frontend_folder}}/
	yarn cy:open
	EXIT_STATUS="$?"
	just kill-servers
	exit "$EXIT_STATUS"

# Execute all the integration tests in headless mode.
test-e2e-headless: run-servers
	#!/usr/bin/env bash
	cd {{frontend_folder}}/
	yarn cy:run
	EXIT_STATUS="$?"
	just kill-servers
	exit "$EXIT_STATUS"
