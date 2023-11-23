#!/bin/bash

# Script to stop, rebuild, and restart Docker containers using Docker Compose

# Navigate to the directory containing docker-compose.yml
# cd /path/to/your/docker/compose/directory

# Run the desired Docker Compose commands
docker-compose down && docker-compose build && docker-compose up -d