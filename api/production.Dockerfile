# Use Maven to build the app
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Use a lightweight JRE to run the app
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
RUN curl -o /app/wait-for-it.sh https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh \
    && chmod +x /app/wait-for-it.sh
EXPOSE 8080
ENTRYPOINT ["/bin/sh", "-c"]
CMD ["java", "-jar", "app.jar"]

#CMD ["/app/wait-for-it.sh ${MYSQL_HOST:-sql8.freesqldatabase.com}:${MYSQL_PORT:-3306} -t 30 -- java -jar app.jar"]

#ENTRYPOINT ["./wait-for-it.sh", "mysql:3306", "--", "java", "-jar", "app.jar"]