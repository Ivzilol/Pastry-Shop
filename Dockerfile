FROM amazoncorretto:17.0.6

COPY target/pastry-shop-0.0.1-SNAPSHOT.jar pastry-shop.jar

ENTRYPOINT ["java", "-jar", "/pastry-shop.jar"]