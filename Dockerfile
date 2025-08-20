FROM jenkins/jenkins:lts
USER root

RUN apt update && apt install -y chromium
ENV CHROME_BIN=/usr/bin/chromium

RUN apt update && apt -y install -y docker.io

RUN docker --version

USER jenkins