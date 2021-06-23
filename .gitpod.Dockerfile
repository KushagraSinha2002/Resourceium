FROM gitpod/workspace-postgres

RUN bash -c "source /home/gitpod/.sdkman/bin/sdkman-init.sh && sdk install java "

ENV DB_PASSWORD=password
ENV DB_USER=username
ENV DB_DATABASE=server_db
ENV DJANGO_DATABASE_NAME=website-db
ENV DJANGO_DATABASE_USER=username
ENV DJANGO_DATABASE_PASSWORD=admin-password
ENV STORAGE_SERVER=http://localhost:8080/
ENV DJANGO_DEBUG=1
ENV DJANGO_DATABASE_ENGINE=django.db.backends.postgresql
ENV POSTGRES_USER=gitpod
ENV DISABLE_SSL=1

RUN curl --proto '=https' --tlsv1.2 -sSf https://just.systems/install.sh | sudo bash -s -- --to /usr/bin
