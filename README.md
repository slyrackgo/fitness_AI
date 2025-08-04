# fitness_AI🏋️‍♀️

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)
- [Acknowledgements](#acknowledgements)

## Introduction

> This repository contains the backend microservices for a Fitness AI application. The project is built using Java and the Spring Boot framework, following a microservice architecture to provide a scalable and modular solution for a modern fitness tracking platform.

This project aims to [describe the main goal of your project]. It leverages [mention key technologies used, e.g., machine learning, computer vision] to [explain what the project does, e.g., provide personalized workout recommendations, track fitness progress, analyze exercise form].

## Features

> The application is composed of several independent services that work together to provide a comprehensive set of features:

*   **[User Service:]:** [Manages user registration, authentication, and profile information.]
*   **[Activity Service:]:** [Tracks and stores user fitness data, such as workouts, runs, and other physical activities.]
*   **[AI Service:]:** [The core intelligence of the platform, responsible for processing user data, providing personalized workout recommendations, or analyzing form and performance]
*   **[Eureka Service:]:** [Acts as the service registry, allowing the other microservices to find and communicate with each other dynamically.]

## Installation

> To get a local copy up and running, follow these simple steps.

1.  Clone the repository:

    Java Development Kit (JDK) 11 or higher
    Apache Maven
    An IDE like IntelliJ IDEA or VS Code (optional)
    git clone https://github.com/slyrackgo/fitness_AI.git
    cd fitness_AI

    # Build the Eureka service first
cd eureka/eureka
mvn clean install

# Build other services
cd ../../AI_Service/AI_Service
mvn clean install

cd ../../activityService/activityService
mvn clean install

cd ../../userService/userService
mvn clean install



