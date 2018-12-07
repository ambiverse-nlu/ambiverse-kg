# AmbiverseNLU Knowledge Graph

[![Build Status](https://travis-ci.org/ambiverse-nlu/ambiverse-kg.svg?branch=master)](https://travis-ci.org/ambiverse-nlu/ambiverse-kg.svg?branch=master)
[![DockerHub](https://img.shields.io/badge/docker-available-blue.svg?logo=docker)](https://hub.docker.com/r/ambiverse/ambiverse-kg)


The AmbiverseNLU Knowledge Graph web service allows you to search and query the [YAGO](http://yago-knowledge.org) Knowledge Graph (imported to Neo4j), 
providing you with detailed information for all canonical entities and the categories that are assigned to them (e.g. person, organization, location, etc.). 
The Knowledge Graph Service is particularly suited to be run as a second step after ambiguous names that occur in natural language texts have been matched 
onto canonical entities by the [AmbiverseNLU](https://github.com/ambiverse-nlu/ambiverse-nlu) service.

Try the complete [demo](http://ambiversenlu.mpi-inf.mpg.de) at http://ambiversenlu.mpi-inf.mpg.de

## Quick Start

### Call the Web Service using Docker

Starting the AmbiverseNLU KG as web service (with Neo4j backend) using Docker is simple, using docker-compose:

~~~~~~~~~~~~
docker-compose -f docker-compose/service-kg.yml up
~~~~~~~~~~~~

Wait for some time (depending on your internet connection and CPU speed it can easily take more than an hour), then call the service:

~~~~~~~~~~~~
ccurl -X POST -H "Content-Type: application/json" \
      -d '[
            "http://www.wikidata.org/entity/Q1137062",
            "http://www.wikidata.org/entity/Q1359568"
          ]' \
      "http://localhost:8080/knowledgegraph/entities"
~~~~~~~~~~~~

## Alternative Ways to Run

### Start the Neo4j Database Backend

Start the Neo4j database backend with the fully multilingual knowledge graph:
~~~~~~~~
docker run -d --restart=always --name kg-db-neo4j \
	-p 7474:7474 -p 7687:7687 \
	-e NEO4J_dbms_active__database=yago_aida_en20180120_cs20180120_de20180120_es20180120_ru20180120_zh20180120.db \
	-e NEO4J_dbms_memory_pagecache_size=8G \
	-e NEO4J_dbms_memory_heap_initial__size=8G \
	-e NEO4J_dbms_memory_heap_max__size=12G \
	-e NEO4J_dbms_connectors_default__listen__address=0.0.0.0 \
	-e NEO4J_dbms_security_procedures_unrestricted=apoc.* \
	-e NEO4J_AUTH=neo4j/neo4j_pass \
	-e DUMP_NAME=yago_aida_en20180120_cs20180120_de20180120_es20180120_ru20180120_zh20180120 \
	--ulimit=nofile=40000:40000 \
	ambiverse/kg-db-neo4j
~~~~~~~~

Then start the AmbiverseNLU Knowledge Graph container by linking the running Neo4j container.
~~~~~~~~
docker run -d --restart=always --name ambiverse-kg \
 -p 8080:8080 \
 --link kg-db-neo4j:kg-db \
 ambiverse/ambiverse-kg
~~~~~~~~

### Start the Web Service using Maven and Jetty from Source Code

1. Adapt the database configuration. You need to adapt the `neo4j.properties` file in `src/main/resources/default/` by providing the `neo4j.url`, `neo4j.user` and `neo4j.password.  
2. Start the web service by executing the following script:

~~~~~~~~~~~~
./scripts/start_webservice.sh
~~~~~~~~~~~~


You can the `MAVEN_OPTS` in the script if you want to change the port and the available memory. 

## Database dumps 
The database dumps can be downloaded from [http://ambiversenlu-download.mpi-inf.mpg.de/](http://ambiversenlu-download.mpi-inf.mpg.de/). The database docker images will download them automatically.

## Further Information

* AmbiverseNLU project website: [http://www.mpi-inf.mpg.de/ambiverse-nlu/](http://www.mpi-inf.mpg.de/ambiverse-nlu/)


## AmbiverseNLU License

[Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0.html)

## Maintainers and Contributors

Current Maintainers (in alphabetical order):

* Dragan Milchevski
* Ghazale Haratinezhad Torbati
* Johannes Hoffart

Contributors (in alphabetical order):
* Daniel Bär
* Dragan Milchevski
* Ghazale Haratinezhad Torbati
* Johannes Hoffart
* Luciano Del Corro