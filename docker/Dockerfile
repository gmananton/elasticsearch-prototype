FROM openjdk:13

ADD ./app.jar /app/app.jar
RUN sh -c 'touch /app/app.jar'

WORKDIR /app
CMD ["java", "-Duser.country=EN", "-Duser.timezone=GMT", "-Djava.security.egd=file:/dev/./urandom", "-Xmx256m", "-jar", "/app/app.jar"]

EXPOSE 8080
