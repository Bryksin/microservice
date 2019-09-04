FROM adoptopenjdk/openjdk12:alpine-jre

ENV SERVICE_NAME teacher-service
ENV APP_FILE $SERVICE_NAME-0.0.1-SNAPSHOT.jar
ENV APP_HOME /app

COPY $SERVICE_NAME/build/libs/$APP_FILE $APP_HOME/
WORKDIR $APP_HOME

EXPOSE 8080

ENTRYPOINT ["sh", "-c"]
CMD ["exec java -jar -Dspring.profiles.active=docker $APP_FILE"]