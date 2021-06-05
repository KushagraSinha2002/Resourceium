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
        then
            export API_URL="$(gp url 8000)/"
            export STORAGE_SERVER="$(gp url 8080)/"
        else
            export API_URL="http://localhost:8000/"
            export STORAGE_SERVER="http://localhost:8080/"
    fi
    { cd "{{storage_folder}}" && poetry run python manage.py runserver 0.0.0.0:8080 & \
    cd "{{backend_folder}}" && mvn spring-boot:run -D spring-boot.run.profiles=dev & \
    cd "{{frontend_folder}}" && yarn dev; }

# Setup the dependencies for all the microservices.
setup:
    #!/usr/bin/env bash
    cd "{{frontend_folder}}" && yarn install
    cd "../{{storage_folder}}" && poetry install && poetry run python manage.py migrate
    cd "../{{backend_folder}}" && mvn dependency:go-offline -B

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

    cd "../{{storage_folder}}"
    pm2 start "poetry run gunicorn storage.wsgi:application -b 127.0.0.1:8080"

    cd "../{{backend_folder}}"
    pm2 start "mvn spring-boot:run -Dspring-boot.run.profiles=dev"

    just poll

# Poll the servers to ensure they are running.
poll:
    #!/usr/bin/env bash
    wait-on -v http-get://127.0.0.1:3000/
    wait-on -v http-get://127.0.0.1:8080/
    wait-on -v http-get://127.0.0.1:8000/accounts/all

# Kill the servers that were spawned by the `run-servers` recipe.
kill-servers:
    #!/usr/bin/env bash
    pm2 kill

# Execute all the integration tests in interactive mode.
test-e2e:
    #!/usr/bin/env bash
    pm2 start "just default"
    just poll
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
