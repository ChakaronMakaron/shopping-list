Steps to run in DOCKER:

1. ./mvnw -DskipTests clean package
2. docker build . -t shopping-list:1.0
3. docker run -d --name="shopping-list" -p 8080:8080 shopping-list:1.0
