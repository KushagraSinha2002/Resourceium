FROM gitpod/workspace-mysql

RUN bash -c "source /home/gitpod/.sdkman/bin/sdkman-init.sh && sdk install java 15.0.2.7.1-amzn"

ENV ROOT_PASSWORD=password
ENV PASSWORD=password
ENV DATABASE_USER=gitpod
ENV DATABASE_NAME=new_db
