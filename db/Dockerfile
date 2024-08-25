# Use the official MySQL image
FROM mysql:latest

# Set environment variables for MySQL
ENV MYSQL_ALLOW_EMPTY_PASSWORD yes
ENV MYSQL_DATABASE=DB_AUCTION
ENV MYSQL_COLLATION=utf8mb4_general_ci

# Copy the SQL script into the container
COPY init2.sql /docker-entrypoint-initdb.d/init2.sql

# Expose MySQL port
EXPOSE 3306
