#!/bin/bash

echo "Starting the application... "
CMD="java -jar app.jar --logging.level.org.springframework.web=ERROR"

if [ -n "$MVEODB_PORT_3306_TCP_ADDR" ]
then
	echo "mysql host: $MVEODB_PORT_3306_TCP_ADDR"
	CMD="java -jar app.jar --logging.level.org.springframework.web=ERROR --spring.datasource.url=jdbc:mysql://$MVEODB_PORT_3306_TCP_ADDR:3306/mveo"
fi

eval $CMD

echo "application is started"
