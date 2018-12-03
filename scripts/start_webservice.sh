#! /bin/bash
export MAVEN_OPTS="-Djetty.port=8080 -server -Xmx8G"

mvn clean compile
mvn jetty:run
