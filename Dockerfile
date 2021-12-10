FROM openjdk:8-jdk-alpine
COPY build/libs/api.jar /api.jar
ENTRYPOINT java \
$(test -f /log4j.properties && echo "-Dlog4j.configuration=file:/log4j.properties") \
-jar /api.jar