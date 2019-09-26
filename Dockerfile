FROM openjdk:11-alpine
EXPOSE 9000
COPY ./target/blackjack-extended-0.0.1-SNAPSHOT.jar /api/blackjack-extended-0.0.1-SNAPSHOT.jar
CMD java -jar api/blackjack-extended-0.0.1-SNAPSHOT.jar