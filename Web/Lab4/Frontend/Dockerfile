# Установка Node.js для сборки Angular-приложения
FROM node:16 AS build-stage

WORKDIR /app

# Копирование package.json и установка зависимостей
COPY package*.json ./
RUN npm install

# Копирование остальных файлов и сборка
COPY . .
RUN npm run build --prod

# Сервер для статических файлов
FROM nginx:alpine
COPY --from=build-stage /app/dist/frontend /usr/share/nginx/html

# Экспонирование порта
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
