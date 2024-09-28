FROM openjdk:17-oracle

# Create a non-root user to run app as (instead of root)
RUN groupadd app && useradd -g app -s /bin/bash -m app

# Use user "app"
USER app

# Set build arguments for Redis (optional)
ARG REDISHOST
ARG REDISPORT
ARG REDISUSER
ARG REDISPASSWORD

# Copy the jar file into the docker image
COPY target/*.jar app.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app.jar"]