default:
	#!/usr/bin/env bash
	backend_folder="server"
	frontend_folder="client"
	storage_folder="storage"

	pwd="$(pwd)"
	cd "$frontend_folder" && yarn install && cd "$pwd"
	cd "$storage_folder" && poetry install && poetry run python manage.py migrate && cd "$pwd"

	if command -v gp &>/dev/null;
	then URL="$(gp url 8000)"
	else URL="http://localhost:8000/"
	fi

	export PIP_USER=false

	# Start all the servers
	{ cd "$storage_folder" && poetry run python manage.py runserver 0.0.0.0:8080 & \
	cd "$backend_folder" && mvn spring-boot:run -Dspring-boot.run.profiles=dev & \
	cd "$frontend_folder" && API_URL="${URL}" yarn dev; }

