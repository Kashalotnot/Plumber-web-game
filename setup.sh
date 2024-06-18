#!/bin/bash

# Prompt for database configuration
read -p "Enter your PostgreSQL database URL [jdbc:postgresql://localhost:5432/postgres]: " DB_URL
DB_URL=${DB_URL:-jdbc:postgresql://localhost:5432/postgres}

read -p "Enter your PostgreSQL username [postgres]: " DB_USERNAME
DB_USERNAME=${DB_USERNAME:-postgres}

read -sp "Enter your PostgreSQL password [postgres]: " DB_PASSWORD
DB_PASSWORD=${DB_PASSWORD:-postgres}

echo

# Configure database
echo "Configuring database..."
cat <<EOT > src/main/resources/application.properties
spring.datasource.url=$DB_URL
spring.datasource.username=$DB_USERNAME
spring.datasource.password=$DB_PASSWORD
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.hikari.maximumPoolSize=2
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
spring.web.resources.static-locations=classpath:/static/
spring.mvc.static-path-pattern=/**
EOT

echo "Do you want to play the Web UI version or the CLI version?"
echo "1. Web UI"
echo "2. CLI"
read -p "Enter your choice (1 or 2): " choice

run_web() {
    echo "Building and running the Web UI version..."
    ./mvnw clean install -DskipTests
    ./mvnw spring-boot:run
}

run_cli() {
    echo "Building the application..."
    ./mvnw clean install -DskipTests

    echo "Starting the Web Server for backend services..."
    nohup ./mvnw spring-boot:run &

    echo "Running the CLI version..."
    ./mvnw exec:java -Dexec.mainClass="sk.tuke.gamestudio.GamestudioApplication"
}

if [ "$choice" -eq 1 ]; then
    run_web
elif [ "$choice" -eq 2 ]; then
    run_cli
else
    echo "Invalid choice. Exiting."
    exit 1
fi
