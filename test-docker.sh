#!/bin/bash

echo "========================================"
echo "Testing Contacts Backend Locally"
echo "========================================"
echo ""

echo "Step 1: Building Docker image..."
docker build -t contacts-backend-test .

if [ $? -ne 0 ]; then
    echo "ERROR: Docker build failed!"
    exit 1
fi

echo ""
echo "Step 2: Running container..."
echo ""
echo "Starting container on http://localhost:8080"
echo "Press Ctrl+C to stop"
echo ""

docker run -p 8080:8080 \
    -e JDBC_DATABASE_URL=jdbc:postgresql://host.docker.internal:5432/contacts_db \
    -e DB_USERNAME=postgres \
    -e DB_PASSWORD=postgres \
    -e JWT_SECRET=test_secret_key \
    -e FRONTEND_URL=http://localhost:3000 \
    contacts-backend-test
