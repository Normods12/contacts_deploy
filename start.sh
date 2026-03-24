#!/bin/bash

# Render.com provides DATABASE_URL in format:
# postgres://user:password@host:port/database
# We need to convert it to JDBC format:
# jdbc:postgresql://host:port/database

if [ -n "$DATABASE_URL" ]; then
    echo "Converting DATABASE_URL to JDBC format..."
    
    # Extract components from DATABASE_URL
    # Format: postgres://user:password@host:port/database
    PROTO="$(echo $DATABASE_URL | grep :// | sed -e's,^\(.*://\).*,\1,g')"
    URL="$(echo ${DATABASE_URL/$PROTO/})"
    USER="$(echo $URL | grep @ | cut -d@ -f1 | cut -d: -f1)"
    PASS="$(echo $URL | grep @ | cut -d@ -f1 | cut -d: -f2)"
    HOSTPORT="$(echo $URL | grep @ | cut -d@ -f2 | cut -d/ -f1)"
    DB="$(echo $URL | grep / | cut -d/ -f2-)"
    
    # Set JDBC_DATABASE_URL
    export JDBC_DATABASE_URL="jdbc:postgresql://${HOSTPORT}/${DB}"
    export DB_USERNAME="${USER}"
    export DB_PASSWORD="${PASS}"
    
    echo "JDBC_DATABASE_URL: $JDBC_DATABASE_URL"
    echo "DB_USERNAME: $DB_USERNAME"
    echo "Database: $DB"
fi

# Start the application
exec java $JAVA_OPTS -jar /app/app.jar
