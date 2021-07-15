FROM registry-dgt.eni.com/endorsed/openjdk:8-jdk-alpine3.7

ENV JAVA_HOME              /usr/lib/jvm/java-8-openjdk-amd64
ENV JAVA_OPTS              "-Xmx768m"
ENV PATH                   $PATH:$JAVA_HOME/bin

COPY ./artifacts/runnable.jar /app/runnable.jar

WORKDIR /app

EXPOSE 8080

CMD ["/bin/sh", "-c", "java $JAVA_OPTS -jar runnable.jar"]

