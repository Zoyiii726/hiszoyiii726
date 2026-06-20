# ===== 阶段1：构建前端静态资源 =====
FROM node:20-alpine AS build

WORKDIR /app

# 先拷贝依赖清单，利用 docker 层缓存
COPY package.json package-lock.json* ./

# 安装依赖（使用国内镜像加速）
RUN npm config set registry https://registry.npmmirror.com && npm install

# 拷贝源码并构建
COPY . .
RUN npm run build

# ===== 阶段2：nginx 提供静态服务 =====
FROM nginx:stable-alpine

# 清理默认配置，使用项目自带配置
RUN rm /etc/nginx/conf.d/default.conf
COPY nginx.conf /etc/nginx/conf.d/default.conf

# 拷贝构建产物
COPY --from=build /app/dist /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
