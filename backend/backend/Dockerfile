# Sử dụng Node.js làm base image
FROM node:18

# Thiết lập thư mục làm việc trong container
WORKDIR /usr/src/app

# Sao chép file package.json và package-lock.json vào thư mục làm việc
COPY package*.json ./

# Cài đặt các dependencies
RUN npm install

# Sao chép toàn bộ mã nguồn vào container
COPY . .

# Expose port
EXPOSE 3000

# Lệnh khởi động ứng dụng
CMD ["npm", "start"]
