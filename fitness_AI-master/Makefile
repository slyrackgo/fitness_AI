SHELL := /bin/bash

.PHONY: check
check:
	@echo "Running repository health checks..."
	@echo "Building eureka...";
	./eureka/eureka/mvnw -f eureka/eureka/pom.xml -q clean package || exit 1
	@echo "Building userService...";
	./userService/mvnw -f userService/pom.xml -q clean package || exit 1
	@echo "Building activityService...";
	./activityService/activityService/mvnw -f activityService/activityService/pom.xml -q clean package || exit 1
	@echo "Building AI_Service...";
	./AI_Service/AI_Service/mvnw -f AI_Service/AI_Service/pom.xml -q clean package || exit 1
	@echo "All checks passed."
