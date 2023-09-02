FROM openjdk:17
ARG USER_UID=1000

ENV HOME_DIR="/home/coderdkk"
ENV APPS_DIR="/home/coderdkk/apps"

COPY target/financemodule-1.0-SNAPSHOT.jar app.jar


ENTRYPOINT ["java","-jar","/app.jar"]
