FROM anapsix/alpine-java
RUN pwd
RUN ls
RUN ls ./target
COPY ./target/poc-word-operations-0.0.1-SNAPSHOT.jar /home/poc-word-operations-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","/home/poc-word-operations-0.0.1-SNAPSHOT.jar"]
