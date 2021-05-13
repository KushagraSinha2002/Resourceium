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
