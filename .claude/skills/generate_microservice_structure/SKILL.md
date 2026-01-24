---
name: generate-microservice-structure
description: Scaffolds a new microservice with a multi-module Maven structure (app, usecase, database, test) based on a pragmatic Hexagonal Architecture. Use this to bootstrap a new microservice project.
---

# Generate Microservice Structure

## Instructions

This skill generates the foundational Maven multi-module structure for a new microservice.

1.  **Create Directories**: Create a root directory for your new microservice.
2.  **Instantiate Templates**: Copy the `pom.xml` templates from the `templates` directory of this skill into your new project structure. You will have a parent `pom.xml` and one `pom.xml` for each module (`-app`, `-usecase`, `-database`, `-test`).
3.  **Replace Placeholders**: Perform a search and replace for the following placeholders in all `pom.xml` files:
    - `__GROUP_ID__`: The Maven group ID (e.g., `br.com.company`).
    - `__ARTIFACT_ID__`: The base artifact ID for the service (e.g., `user-service`).
    - `__VERSION__`: The project version (e.g., `1.0.0-SNAPSHOT`).
    - `__KOTLIN_VERSION__`: The version of Kotlin to be used (e.g., `2.0.20`).
    - `__JAVA_VERSION__`: The version of Java to be used (e.g., `21`).
4.  **Create Source Directories**: Create the standard `src/main/kotlin` and `src/test/kotlin` directory trees within each module, as appropriate.

## Architecture Overview

- **Parent POM (`pom.xml`):** The root aggregator POM.
- **`usecase` Module:** The core of the application (domain entities, business logic, ports).
- **`app` Module:** The infrastructure layer (REST Controllers, repository implementations, `@SpringBootApplication`).
- **`database` Module:** Contains database schema migration files (e.g., Flyway).
- **`test` Module:** Contains integration and end-to-end tests.

## Examples

### Example 1: Bootstrapping a new `customer-service`

An agent would use this skill to create the base structure for a new microservice named `customer-service`.

1.  The agent identifies that the user wants a new microservice.
2.  It invokes this skill.
3.  It copies the templates from `templates/`.
4.  It replaces `__ARTIFACT_ID__` with `customer-service`, `__GROUP_ID__` with `com.example.customer`, etc., in all `.pom.xml.template` files.
5.  It creates the final directory structure with the populated POM files.