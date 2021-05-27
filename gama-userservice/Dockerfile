FROM adoptopenjdk/openjdk11:armv7l-centos-jre-11.0.11_9
#RUN mvn clean package -DskipTests
COPY ./target/gama-userservice-0.0.1-SNAPSHOT.jar gama-userservice.jar
ENTRYPOINT ["java","-jar","/gama-userservice.jar"]