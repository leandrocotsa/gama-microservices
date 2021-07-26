FROM adoptopenjdk/openjdk11:armv7l-centos-jre-11.0.11_9
#RUN mvn clean package -DskipTests
COPY ./target/gama-productsview-0.0.1-SNAPSHOT.jar gama-productsview.jar
ENTRYPOINT ["java","-jar","/gama-productsview.jar"]