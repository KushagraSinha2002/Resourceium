FROM gitpod/workspace-mysql

RUN bash -c "source /home/gitpod/.sdkman/bin/sdkman-init.sh && sdk install java 15.0.2.7.1-amzn"

ENV MYSQL_ROOT_PASSWORD=password
ENV MYSQL_PASSWORD=password
ENV MYSQL_USER=username
ENV MYSQL_DATABASE=server_db
