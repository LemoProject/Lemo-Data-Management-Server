#!/bin/bash
mvn clean install -DskipTests=true
mvn sonar:sonar
