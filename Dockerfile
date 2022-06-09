FROM maven:3.8.5-openjdk-11
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app/
RUN mvn -f /usr/src/app/pom.xml clean package
WORKDIR /usr/src/app/target
ENTRYPOINT ["java", "-jar", "ROOT.jar"]