FROM node:15.14

ENV APP_ROOT /app

WORKDIR ${APP_ROOT}

COPY package.json ./
COPY yarn.lock ./

RUN yarn install

ENV CHOKIDAR_USEPOLLING=1

COPY . ${APP_ROOT}

ENTRYPOINT [ "yarn", "dev" ]
