#!/bin/bash
set -e
cd /root/task

echo "[INFO] Building and starting all containers..."
docker-compose up -d --build

# Wait for health endpoint
function wait_for_health() {
  local retries=30
  local url="http://localhost:8080/actuator/health"
  for i in $(seq 1 $retries); do
    status=$(curl -s -o /dev/null -w "%{http_code}" $url || true)
    if [ "$status" == "200" ]; then
      echo "[SUCCESS] Video Encoding App is healthy ($url)"
      return 0
    fi
    echo "[INFO] Waiting for service to be healthy... ($i/$retries)"
    sleep 2
done
  echo "[ERROR] Service failed to become healthy."
  docker-compose logs
  exit 1
}

wait_for_health

echo "[INFO] Validating API endpoints..."
curl -f http://localhost:8080/api/jobs || {
  echo "[ERROR] API endpoint not responding."; docker-compose logs; exit 1;
}

echo "[INFO] All containers running and endpoints reachable."
docker ps
