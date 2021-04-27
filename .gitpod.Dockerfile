FROM gitpod/workspace-mysql

RUN bash -c "source /home/gitpod/.sdkman/bin/sdkman-init.sh && sdk install java 15.0.2.7.1-amzn"

ENV spring.profiles.active=dev
ENV ROOT_PASSWORD=password
ENV PASSWORD=password
ENV DATABSE_USER=username
ENV DATABASE_NAME=server_db
