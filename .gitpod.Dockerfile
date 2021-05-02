FROM gitpod/workspace-postgres

RUN bash -c "source /home/gitpod/.sdkman/bin/sdkman-init.sh && sdk install java 15.0.2.7.1-amzn"

ENV DB_PASSWORD=password
ENV DB_USER=username
ENV DB_DATABASE=server_db
ENV DJANGO_DATABASE_NAME=website-db
ENV DJANGO_DATABASE_USER=admin-user
ENV DJANGO_DATABASE_PASSWORD=admin-password
ENV STORAGE_SERVER=http://localhost:8080/
