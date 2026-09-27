FROM node:22-alpine AS build
WORKDIR /app
COPY  package.json .
COPY package-lock.json .
RUN   npm ci
COPY src src
COPY index.html .
COPY public public
COPY vite.config.js .
# produces /app/dist
RUN   npm run build

FROM nginx:alpine
COPY --from=build  /app/dist  /usr/share/nginx/html
EXPOSE 80