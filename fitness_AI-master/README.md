# fitness_AI🏋️‍♀️

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Installation](#installation)
- [Start the User Service](#StartTheUserService)


## Introduction

> This repository contains the backend microservices for a Fitness AI application. The project is built using Java and the Spring Boot framework, following a microservice architecture to provide a scalable and modular solution for a modern fitness tracking platform.

## Features

> The application is composed of several independent services that work together to provide a comprehensive set of features:

*   **User Service:** Manages user registration, authentication, and profile information.
*   **Activity Service::** Tracks and stores user fitness data, such as workouts, runs, and other physical activities.
*   **AI Service::** The core intelligence of the platform, responsible for processing user data, providing personalized workout recommendations, or analyzing form and performance
*   **Eureka Service::** Acts as the service registry, allowing the other microservices to find and communicate with each other dynamically.

# fitness_AI🏋️‍♀️

## Table of Contents

- [Introduction](#introduction)
- [Services (modules)](#services-modules)
- [Requirements](#requirements)
- [Quick checks](#quick-checks)
- [Running services locally](#running-services-locally)


## Introduction

This repository contains the backend microservices for a Fitness AI application. The project is implemented with Spring Boot microservices and built with Maven.

## Services (modules)

The repository contains the following service modules (paths relative to repository root):

- `eureka/eureka` — service registry
- `userService` — user management
- `activityService/activityService` — activity ingestion and tracking
- `AI_Service/AI_Service` — recommendation / AI processing
- `gateway/gateway` and `configServer/configServer` — gateway and centralized configuration (present under `gateway` and `configServer` directories)

## Requirements

- Java: JDK 24 (the Maven modules declare `java.version=24`)
- Apache Maven (the repository contains Maven Wrappers where present)
- Git

Note: The project contains multiple Maven modules that declare `java.version=24`. If you have a different local JDK, install and/or configure JDK 24 for builds and tests.

## Quick checks

To run a quick health/build check across the main services there is a root `Makefile` target `check` and a Windows batch script `scripts\check.bat`.

Linux/macOS (if you have `make`):

```sh
make check
```

Windows (PowerShell / CMD):

```bat
scripts\check.bat
```

## Running services locally

Build and run services individually using the included Maven wrappers where available. Example (build Eureka then others):

```sh
# build registry first
./eureka/eureka/mvnw -f eureka/eureka/pom.xml clean package

# build other modules (examples)
./userService/mvnw -f userService/pom.xml clean package
./activityService/activityService/mvnw -f activityService/activityService/pom.xml clean package
./AI_Service/AI_Service/mvnw -f AI_Service/AI_Service/pom.xml clean package
```

Run JARs (artifact IDs and versions may vary):

```sh
java -jar eureka/eureka/target/*.jar
java -jar userService/target/*.jar
```

If you'd like, I can also add an aggregator `pom.xml` to build everything with a single Maven invocation.

## Running tests

Run unit tests for a single module using the included Maven wrapper. Example:

```sh
# Linux/macOS
./userService/mvnw -f userService/pom.xml test

# Windows
userService\\mvnw.cmd -f userService\\pom.xml test
```

To run a repository-wide quick check (build + tests) use:

```sh
make check
```

CI: a GitHub Actions workflow `CI` runs `make check` on push/PR to `main`/`master`.

Product context and ADRs
------------------------

See `docs/product-context.md` for product flow description and `docs/decisions` for ADRs (architecture decisions). These files help new contributors and automated agents understand constraints and expected behaviors.

Note: The original clone URL in earlier audit documents pointed at https://github.com/slyrackgo/fitness_AI.git — this repository is a workspace copy; update upstream references if needed.




