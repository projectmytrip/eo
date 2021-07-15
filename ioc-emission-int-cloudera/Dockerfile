FROM enidgt.azurecr.io/endorsed/openjdk:8-jdk-alpine3.7

ENV JAVA_HOME              /usr/lib/jvm/java-8-openjdk-amd64
ENV JAVA_OPTS              "\
    -XX:+UnlockExperimentalVMOptions \
    -XX:+UseCGroupMemoryLimitForHeap \
    -XX:MaxRAMFraction=2 \
    "

ENV PATH                   $PATH:$JAVA_HOME/bin

COPY ./artifacts/runnable.jar /app/runnable.jar
#COPY ./artifacts/rootCA_eni_SHA2.pem /app/rootCA_eni_SHA2.pem
COPY ./artifacts/rootCA_eni_SHA2.der /app/rootCA_eni_SHA2.der
COPY ./artifacts/krb5.conf /app/krb5.conf
COPY ./artifacts/ADM-CL1-PMC-IOC-PR@AD02.ENI.INTRANET /app/ADM-CL1-PMC-IOC-PR@AD02.ENI.INTRANET
COPY ./artifacts/ADM-CL1-PMC-IOC-ST@AD02.ENI.INTRANET /app/ADM-CL1-PMC-IOC-ST@AD02.ENI.INTRANET

COPY ./artifacts/eniAuthenticationCAG1.cacert.crt /app/resources/eniAuthenticationCAG1.cacert.crt
COPY ./artifacts/eniMobileCAG1.cacert.crt /app/resources/eniMobileCAG1.cacert.crt
COPY ./artifacts/eniRootCAG1.cacert.crt /app/resources/eniRootCAG1.cacert.crt

RUN keytool -importcert -alias startssl -keystore \
    /usr/lib/jvm/java-1.8-openjdk/jre/lib/security/cacerts -storepass changeit -trustcacerts -file /app/rootCA_eni_SHA2.der -noprompt

RUN keytool -importcert -alias eniauth \ 
    -keystore /usr/lib/jvm/java-1.8-openjdk/jre/lib/security/cacerts -storepass changeit \
    -trustcacerts -file /app/resources/eniAuthenticationCAG1.cacert.crt -noprompt

RUN keytool -importcert -alias enimobile \ 
    -keystore /usr/lib/jvm/java-1.8-openjdk/jre/lib/security/cacerts -storepass changeit \
    -trustcacerts -file /app/resources/eniMobileCAG1.cacert.crt -noprompt

RUN keytool -importcert -alias enirootca \ 
    -keystore /usr/lib/jvm/java-1.8-openjdk/jre/lib/security/cacerts -storepass changeit \
    -trustcacerts -file /app/resources/eniRootCAG1.cacert.crt -noprompt

#RUN openssl x509 -in /app/rootCA_eni_SHA2.pem -inform pem -out /app/rootCA_eni_SHA2.der -outform der

WORKDIR /app

EXPOSE 8080

CMD ["/bin/sh", "-c", "java $JAVA_OPTS -jar runnable.jar"]
