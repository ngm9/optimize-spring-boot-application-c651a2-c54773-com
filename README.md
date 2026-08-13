### Objectives
- Achieve production-grade reliability by orchestrating the microservice and database with proper startup order and health awareness.
- Reduce container image size for faster deployment and lower disk usage.
- Enforce clear CPU and memory boundaries for the Java service to prevent resource contention and OOMKilled errors.
- Deliver observable deployments with appropriate logging configuration and container monitoring.
- Secure and harden the Docker deployment to minimize vulnerabilities.

### How to Verify
- Compare the optimized image size against the initial baseline for reduction.
- Observe that the Java application remains stable and responsive even under high concurrent loads.
- Validate that resources are properly constrainedâ€”no OOMKills or excessive CPU usageâ€”using Dockerâ€™s inspection and monitoring tools.
- Confirm job status API responses and asynchronous processing flow under real load.
- Inspect Docker Compose logs and metrics for visibility and stability indicators.

### Helpful Tips
- Consider building Docker images in phases to reduce final size and improve build caching.
- Think about which build and runtime artifacts are essential, and prune unnecessary files.
- Explore JVM options for memory tuning and their effects within containers.
- Review Docker Compose strategies for multitier orchestration: set service dependencies and tune CPU/memory allocations.
- Consider how logging and monitoring can provide visibility during high concurrency loads.
