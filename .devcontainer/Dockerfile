FROM ignisda/developr-workspace

RUN sudo apk add libpq postgresql-dev openjdk11-jre openjdk11-jdk maven git-subtree tzdata

RUN sudo npm add pm2 wait-on -g --unsafe-perms && \
    sudo cp /usr/share/zoneinfo/Asia/Kolkata /etc/localtime
