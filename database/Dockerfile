FROM mysql:8.0

ENV MYSQL_DATABASE=sql8799295
ENV MYSQL_USER=sql8799295
ENV MYSQL_PASSWORD=ztyb4yEjR9
ENV MYSQL_ROOT_PASSWORD=ztyb4yEjR9
ENV MYSQL_HOST=sql8.freesqldatabase.com
ENV MYSQL_PORT=3306

#COPY ./init.sql /docker-entrypoint-initdb.d/

CMD ["mysqld", "--default-authentication-plugin=mysql_native_password", "--character-set-server=utf8mb4", "--collation-server=utf8mb4_unicode_ci"]