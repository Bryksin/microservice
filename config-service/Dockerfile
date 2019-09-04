FROM adoptopenjdk/openjdk12:alpine-jre

ENV SERVICE_NAME config-service
ENV APP_FILE $SERVICE_NAME-0.0.1-SNAPSHOT.jar
ENV APP_HOME /app

COPY $SERVICE_NAME/build/libs/$APP_FILE $APP_HOME/
WORKDIR $APP_HOME

EXPOSE 8888

ENTRYPOINT ["sh", "-c"]
CMD ["exec java -jar $APP_FILE"]