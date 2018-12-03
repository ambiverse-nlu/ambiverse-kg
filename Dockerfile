FROM maven:3.3.9-jdk-8
ENV AIDA_CONF de_en_es_zh_20160701_cass
WORKDIR /knowledgegraph
ADD pom.xml /knowledgegraph/pom.xml
ADD src /knowledgegraph/src

RUN mvn clean compile -U
ENV MAVEN_OPTS -Djetty.port=8080 -Xmx6G
ENTRYPOINT ["mvn", "jetty:run"]
EXPOSE 8080