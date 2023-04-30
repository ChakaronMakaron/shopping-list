FROM openjdk:11
WORKDIR /app
COPY target/shopping-list-1.0.jar shopping-list-1.0.jar
ENTRYPOINT [ "java", "-jar", "shopping-list-1.0.jar" ]
