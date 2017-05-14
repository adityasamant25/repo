FROM java:8
EXPOSE 8080
ADD /target/course-api-mysql-0.0.1-SNAPSHOT.jar course-api-data.jar
ENTRYPOINT ["java","-jar","course-api-data.jar"]