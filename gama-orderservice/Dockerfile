FROM adoptopenjdk/openjdk11:armv7l-centos-jre-11.0.11_9
#RUN mvn clean package -DskipTests
COPY ./target/gama-orderservice-0.0.1-SNAPSHOT.jar gama-orderservice.jar
ENTRYPOINT ["java","-jar","/gama-orderservice.jar"]