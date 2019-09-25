FROM openjdk:8-jdk-alpine
MAINTAINER bartekmarcysiak@gmail.com
EXPOSE 9000
CMD java -jar target/blackjack-extended-0.0.1-SNAPSHOT.jar
ADD ./target/blackjack-extended-0.0.1-SNAPSHOT.jar /api/blackjack-extended-0.0.1-SNAPSHOT.jar

