#COMPILE STAGE
FROM maven:3.8.5-openjdk-17-slim AS MAVEN_BUILD_IMAGE

# CREATING A FOLDER THAT WILL RECEIVE THE SOURCE CODE
ENV APP_HOME=/app/src
RUN mkdir -p $APP_HOME
WORKDIR $APP_HOME

# COPY SOURCE CODE TO CREATED FOLDER
COPY . $APP_HOME

# COMPILING
RUN mvn clean package -Dmaven.test.skip=true


#ESTAGIO DE EXECUCAO
FROM openjdk:17.0.1
COPY --from=builder /build/target/*.jar /app/bankAccount.jar
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/bankAccount.jar"]