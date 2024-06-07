## Dùng official SQL Server 2019 image của Microsoft
#FROM mcr.microsoft.com/mssql/server:2019-latest
#
## Biến môi trường
#ENV ACCEPT_EULA=Y
#ENV SA_PASSWORD=Thanhth@nh1
#
## Copy the SQL script vào container
#COPY init.sql /usr/src/app/init.sql
#
## Chạy lệnh trong file sql
#RUN /opt/mssql/bin/sqlservr & sleep 30 \
#    && /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P $SA_PASSWORD -i /usr/src/app/init.sql \
#    && pkill sqlservr
#
#EXPOSE 1433
## Khởi động sql server
#CMD ["/opt/mssql/bin/sqlservr"]

FROM mcr.microsoft.com/mssql/server:2019-latest

# Set environment variables
ENV ACCEPT_EULA=Y
ENV SA_PASSWORD=Thanhth@nh1

# Create a directory for the SQL scripts
WORKDIR /usr/src/app

# Copy the init.sql script into the container
COPY .db/init.sql .

# Run SQL Server and execute the init.sql script during image build
RUN /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P $SA_PASSWORD -d master -i init.sql

# Expose the default SQL Server port
EXPOSE 1433

# Run SQL Server
CMD ["/opt/mssql/bin/sqlservr"]


