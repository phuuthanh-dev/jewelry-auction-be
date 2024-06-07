#FROM maven:3.8.4-openjdk-17-slim AS build
#WORKDIR /app
#COPY . .
#RUN mvn clean package -DskipTests
#
#FROM openjdk:17-jdk-alpine
#WORKDIR /app
#COPY --from=build /app/target/*.jar /app/app.jar
#EXPOSE 8080
#CMD ["java", "-jar", "/app/app.jar"]



FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-alpine AS final
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar
EXPOSE 8080

# SQL Server Setup
FROM mcr.microsoft.com/mssql/server:2019-latest AS sqlserver
# Environment Variables
ENV ACCEPT_EULA=Y
ENV SA_PASSWORD=Thanhth@nh1
# Copy the SQL script into the container
COPY ./db/init.sql /usr/src/app/init.sql
# Run SQL commands
RUN /opt/mssql/bin/sqlservr & sleep 30 \
    && /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P $SA_PASSWORD -i /usr/src/app/init.sql \
    && pkill sqlservr

# Expose SQL Server port
EXPOSE 1433

# Start the SQL Server
CMD ["/opt/mssql/bin/sqlservr"]
