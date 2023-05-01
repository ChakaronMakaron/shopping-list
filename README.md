--------------------

Steps to run in DOCKER:

1. clone and cd to project root
2. ./mvnw -DskipTests clean package
3. docker build . -t shopping-list:1.0
4. docker run -d --name="shopping-list" -p 8080:8080 shopping-list:1.0

--------------------

Login:

Registration form expects non-null first name and last name,
valid email (may be fake)
arbitrary password

To avoid registration, it is possible to use embedded user
with credentials:
user@ukr.net
test123
