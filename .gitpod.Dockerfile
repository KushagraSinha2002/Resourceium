FROM gitpod/workspace-mysql

RUN bash -c "source /home/gitpod/.sdkman/bin/sdkman-init.sh && sdk install java 15.0.2.7.1-amzn"

ENV spring.profiles.active=dev
ENV MYSQL_DATABASE=server_db

RUN bash -c "cd client && yarn install"
