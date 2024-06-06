FROM mcr.microsoft.com/mssql/server:2019-latest

ENV ACCEPT_EULA=Y
ENV SA_PASSWORD=Thanhth@nh1

COPY init.sql /docker-entrypoint-initdb.d/init.sql

EXPOSE 1433
