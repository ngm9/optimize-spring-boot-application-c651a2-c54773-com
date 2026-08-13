#!/bin/bash
set -e
cd /root/task || true

echo "[INFO] Stopping and removing all containers..."
docker-compose -f /root/task/docker-compose.yml down --volumes --remove-orphans || true
sleep 2

echo "[INFO] Removing Docker images for the task..."
docker rmi -f $(docker images -q | grep -E 'video|openjdk') || true
sleep 1

echo "[INFO] Running docker system prune..."
docker system prune -a --volumes -f
sleep 1

echo "[INFO] Removing /root/task directory and build files..."
rm -rf /root/task || true
rm -rf /root/task/target /root/task/build /root/task/.gradle || true

echo "[INFO] Cleanup completed successfully! Droplet is now clean."
