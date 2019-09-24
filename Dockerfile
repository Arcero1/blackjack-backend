FROM ubuntu:18.04

RUN apt-get update && apt-get install -y apache2 && apt-get clean && rm -rf /var/lib/apt/lists/*

RUN apt-get install maven

ENV APACHE_RUN_USER www-data
ENV APACHE_RUN_GROUP www-data
ENV APACHE_LOG_DIR /var/log/apache2

COPY . /var/www

EXPOSE 80

CMD ["/usr/sbin/apache2", "-D", "FOREGROUND"]

