#!/bin/bash
mvn -o clean install -DskipTests=true
mvn -o sonar:sonar
