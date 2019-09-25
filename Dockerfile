FROM openjdk:8-jdk-alpine
MAINTAINER bartekmarcysiak@gmail.com
EXPOSE 9000
ADD ./target/blackjack-extended-0.0.1-SNAPSHOT.jar /api/blackjack-extended-0.0.1-SNAPSHOT.jar
CMD java -jar api/blackjack-extended-0.0.1-SNAPSHOT.jar

