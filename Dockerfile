#COMPILE STAGE
FROM maven:3.8.5-openjdk-17-slim AS MAVEN_BUILD_IMAGE

# CREATING A FOLDER THAT WILL RECEIVE THE SOURCE CODE
ENV APP_HOME=/app/src
RUN mkdir -p $APP_HOME
WORKDIR $APP_HOME

# COPY SOURCE CODE TO CREATED FOLDER
COPY . $APP_HOME

RUN chmod 777 -R $APP_HOME

# COMPILING
RUN --mount=type=cache,target=/root/.m2 mvn -f $APP_HOME/pom.xml clean package -Dmaven.test.skip=true


FROM openjdk:17.0.2 AS RUNTIME
COPY --from=MAVEN_BUILD_IMAGE /app/src/target/*.jar /app/bankAccount.jar
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/bankAccount.jar"]