FROM maven:3.6-jdk-8-slim AS packager

RUN mkdir -p /transaction

WORKDIR /transaction

ADD ./pom.xml .
ADD ./docker-entrypoint.sh /

RUN mvn install -B -f ./pom.xml

COPY ./ .

RUN mvn package -Dmaven.test.skip=true && \
    mv ./target/*.jar /run/transaction.jar

FROM openjdk:8-slim

COPY --from=packager /run/transaction.jar /var/picpay/transaction/transaction.jar
COPY --from=packager /docker-entrypoint.sh /docker-entrypoint.sh

RUN chmod +x /docker-entrypoint.sh

EXPOSE 8001

ENTRYPOINT [ "bash", "/docker-entrypoint.sh" ]