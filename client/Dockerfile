FROM node:15.14-alpine

ENV APP_ROOT /app

WORKDIR ${APP_ROOT}

RUN apk update && apk add g++ make python2 libgcc libstdc++ linux-headers

COPY package.json ./
COPY yarn.lock ./

RUN yarn install

ENV CHOKIDAR_USEPOLLING=1

COPY . ${APP_ROOT}

CMD [ "yarn", "dev" ]
