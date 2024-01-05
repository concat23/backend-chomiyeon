FROM maven:3.8.2 AS build
WORKDIR /backend-chomiyeon
COPY pom.xml /backend-chomiyeon
RUN mvn dependency:resolve
COPY . /backend-chomiyeon
RUN mvn clean
RUN mvn package -DskipTests

FROM openjdk:8-jdk-oracle
COPY --from=build /backend-chomiyeon/target/*.jar backend-chomiyeon.jar
EXPOSE 8080
CMD ["java","-jar","backend-chomiyeon.jar"]