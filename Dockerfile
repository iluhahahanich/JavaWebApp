# FROM maven:latest AS build
# COPY ./src /home/UPweb/src
# COPY ./pom.xml /home/UPweb/pom.xml
# RUN mvn -f /home/UPweb/pom.xml clean package

FROM tomcat:9.0.44-jdk15-openjdk

# COPY --from=build /home/UPweb/target/UPweb.war /usr/local/tomcat/webapps/
COPY ./target/UPweb.war /usr/local/tomcat/webapps/

WORKDIR /webapps

EXPOSE 8080
CMD ["catalina.sh", "run"]
