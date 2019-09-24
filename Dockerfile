FROM openjdk:8-jdk-alpine
MAINTAINER bartekmarcysiak@gmail.com
EXPOSE 3000
CMD java -jar /data/hello-world-0.1.0.jar
ADD ./data/hello-world-0.1.0.jar /data/hello-world-0.1.0.jar

