# Stage 1: Build Angular application
FROM node:24 AS build

WORKDIR /app

COPY package*.json ./

RUN npm install -g npm@11.6.1

RUN npm ci

COPY . .

RUN npm run build

# Stage 2: Serve with Nginx
FROM nginx:alpine

COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]