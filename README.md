Steps to run in DOCKER:

1. clone and cd to project root
2. ./mvnw -DskipTests clean package
3. docker build . -t shopping-list:1.0
4. docker run -d --name="shopping-list" -p 8080:8080 shopping-list:1.0
