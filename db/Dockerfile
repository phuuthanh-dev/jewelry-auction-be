# Dùng official SQL Server 2019 image của Microsoft
FROM mcr.microsoft.com/mssql/server:2019-latest

# Copy the SQL script vào container
COPY init.sql /usr/src/app/init.sql

# Chạy lệnh trong file sql
RUN /opt/mssql/bin/sqlservr & sleep 30 \
    && /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P Thanhth@nh1 -i /usr/src/app/init.sql \
    && pkill sqlservr

EXPOSE 1433
# Khởi động sql server
CMD ["/opt/mssql/bin/sqlservr"]