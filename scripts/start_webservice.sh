#! /bin/bash
export MAVEN_OPTS="-Djetty.port=8080 -server -Xmx8G"
mvn jetty:run
