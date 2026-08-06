# Testing guide

This document describes how to run unit and integration tests locally and in CI.

Unit tests
- Each module includes unit tests under `src/test/java` and can be run with the module's Maven wrapper:

  ```sh
  ./userService/mvnw -f userService/pom.xml test
  ```

CI
- The `CI` workflow runs `make check` which builds and runs tests for primary modules.
- JaCoCo coverage reports are uploaded as artifacts from CI jobs.

Integration tests
- Integration tests should use `application-test.yml` and Testcontainers to provide reproducible databases and message brokers.
- This repository does not yet include Testcontainers-based integration tests; add them under `src/test/java` and document prerequisites here.

Recommended next steps
- Add Testcontainers dependencies to modules requiring DB/RabbitMQ/Mongo.
- Create an `application-test.yml` that points to containerized endpoints.
- Add a fast integration job to CI that runs a small subset of integration tests.
