FROM        adoptopenjdk/openjdk11:alpine-slim
ENV         NAME_JAR="pet_finances"
ENV         packageFile="/app/${NAME_JAR}*.jar"
RUN         mkdir /app
WORKDIR     /app
COPY        /docker/entrypoint/start-service.sh /bin/
RUN         chmod +x /bin/start-service.sh
COPY        /source-code/infrastructure/build/libs/${NAME_JAR}*.jar /app/
ENTRYPOINT  /bin/start-service.sh

