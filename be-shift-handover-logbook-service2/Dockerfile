FROM enidgt.azurecr.io/eni/openjdk-8-alpine-eni-ca-trusted:1.0.0

ENV JAVA_HOME              /usr/lib/jvm/java-1.8-openjdk
ENV JAVA_OPTS              "-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=2"
ENV PATH                   $PATH:$JAVA_HOME/bin

COPY ./artifacts/runnable.jar /app/runnable.jar

WORKDIR /app

EXPOSE 8080

CMD ["/bin/sh", "-c", "java $JAVA_OPTS -jar runnable.jar"]