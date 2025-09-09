FROM mysql:8.0

ENV MYSQL_DATABASE=db-critik-app
ENV MYSQL_USER=user
ENV MYSQL_PASSWORD=password
ENV MYSQL_ROOT_PASSWORD=root

COPY ./init.sql /docker-entrypoint-initdb.d/

CMD ["mysqld", "--default-authentication-plugin=mysql_native_password", "--character-set-server=utf8mb4", "--collation-server=utf8mb4_unicode_ci"]