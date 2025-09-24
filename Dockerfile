version: '3.8'

services:
  backend:
    build: ./CinevaBackend
    container_name: cineva-backend
    ports:
      - "8080:8080" # Map backend container port 8080 to host port 8080
    restart: always # Ensures the service restarts automatically if it fails
    # Add a healthcheck to ensure the backend is ready before the frontend starts
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8080/"]
      interval: 30s
      timeout: 10s
      retries: 5

  frontend:
    build: ./docker-frontend
    container_name: cineva-frontend
    ports:
      - "5173:80"  # Map Nginx's 80 to your local 5173
    environment:
      # Use a host-network accessible URL for the API.
      # The service name 'backend' will resolve to the backend container's IP.
      - VITE_API_URL=http://backend:8080/api
    depends_on:
      backend:
        condition: service_healthy # Wait for the backend to be healthy before starting
    restart: always # Ensures the service restarts automatically if it fails
