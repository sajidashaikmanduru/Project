version: '3.8'

services:
  backend:
    build: ./CinevaBackend
    container_name: cineva-backend
    ports:
      - "8080:8080"

  frontend:
    build: ./docker-frontend
    container_name: cineva-frontend
    ports:
      - "5173:80"   # Map Nginx's 80 to your local 5173
    environment:
      - VITE_API_URL=http://backend:8080/api
    depends_on:
      - backend
