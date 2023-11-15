FROM openjdk:18

COPY target/pastry-shop-0.0.1-SNAPSHOT.jar pastry-shop-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/pastry-shop-0.0.1-SNAPSHOT.jar"]