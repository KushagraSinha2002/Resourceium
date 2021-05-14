ARG VARIANT=3.8
FROM mcr.microsoft.com/vscode/devcontainers/python:${VARIANT}
ENV PYTHONUNBUFFERED 1
ENV PYTHONDONTWRITEBYTECODE 1

# Update args in docker-compose.yaml to set the UID/GID of the "$USERNAME" user.
ARG USER_UID=1000
ARG USER_GID=$USER_UID
ARG USERNAME=vscode
RUN if [ "$USER_GID" != "1000" ] || [ "$USER_UID" != "1000" ]; then groupmod --gid $USER_GID $USERNAME && usermod --uid $USER_UID --gid $USER_GID $USERNAME; fi

# Install Node.js
ARG INSTALL_NODE="true"
ARG NODE_VERSION="lts/*"
RUN if [ "${INSTALL_NODE}" = "true" ]; then su $USERNAME -c "umask 0002 && . /usr/local/share/nvm/nvm.sh && nvm install ${NODE_VERSION} 2>&1"; fi

# Install required development packages
RUN apt update \
     && export DEBIAN_FRONTEND=noninteractive \
     && apt -y install --no-install-recommends "fish" "exa" "neovim" "yarn" "git" "maven" "openjdk-11-jre" "openjdk-11-jdk" \
     && curl -sSL https://github.com/sharkdp/bat/releases/download/v0.18.0/bat-musl_0.18.0_amd64.deb -o /tmp/bat.deb \
     && dpkg -i /tmp/bat.deb \
     && wget -qO- https://toolbelt.heroku.com/install-ubuntu.sh | sh

ENV CYPRESS_CACHE_FOLDER=/home/${USERNAME}/Cypress
RUN mkdir -p $CYPRESS_CACHE_FOLDER && sudo chown ${USERNAME}:${USERNAME} -R $CYPRESS_CACHE_FOLDER \
     && npm install --global --unsafe-perms pm2 wait-on

RUN apt-get install --no-install-recommends -y \
          libgtk2.0-0 libgtk-3-0 libnotify-dev libgconf-2-4 libgbm-dev libnss3 libxss1 libasound2 libxtst6 xauth xvfb \
          fonts-arphic-bkai00mp fonts-arphic-bsmi00lp fonts-arphic-gbsn00lp fonts-arphic-gkai00mp fonts-arphic-ukai zip \
          fonts-arphic-uming ttf-wqy-zenhei ttf-wqy-microhei xfonts-wqy fonts-liberation libappindicator3-1 xdg-utils \
     && rm -rf /var/lib/apt/lists/*

ENV DBUS_SESSION_BUS_ADDRESS=/dev/null
ENV CHROME_VERSION 86.0.4240.193
RUN wget -O /usr/src/google-chrome-stable_current_amd64.deb "http://dl.google.com/linux/chrome/deb/pool/main/g/google-chrome-stable/google-chrome-stable_${CHROME_VERSION}-1_amd64.deb" && \
  dpkg -i /usr/src/google-chrome-stable_current_amd64.deb ; \
  apt-get install -f -y && \
  rm -f /usr/src/google-chrome-stable_current_amd64.deb

RUN google-chrome --version

# disable shared memory X11 affecting Cypress v4 and Chrome
# https://github.com/cypress-io/cypress-docker-images/issues/270
ENV QT_X11_NO_MITSHM=1
ENV _X11_NO_MITSHM=1
ENV _MITSHM=0
