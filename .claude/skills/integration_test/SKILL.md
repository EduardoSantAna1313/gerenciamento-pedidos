---
name: integration-test
description: Generates a Spring Boot integration test for a service class, including a base test class with Testcontainers setup. Use this to create new integration tests for services.
---

# Add Integration Test

## Instructions

This skill generates a template for a Spring Boot integration test.

1.  **Check for Base Class**: Verify if a suitable base test class (like the one in `BaseIntegrationTest.kt.template`) already exists in the `__ARTIFACT_ID__-test` module. If not, create one first. This base class is responsible for setting up the test environment, including starting Docker containers via Testcontainers.
2.  **Instantiate Test Template**: Copy the `IntegrationTest.kt.template` to the test module, typically under `__ARTIFACT_ID__-test/src/test/kotlin/__GROUP_PATH__/service/`.
3.  **Rename File**: Rename the file to `__EntityName__ServiceTest.kt`, where `__EntityName__` is the feature you are testing.
4.  **Update Base Class**: Ensure the new test class extends the correct base test class.
5.  **Replace Placeholders**: Perform a search and replace for the following placeholders in the new test file:
    - `__GROUP_ID__`: The Maven group ID (e.g., `br.com.company`).
    - `__EntityName__`: The singular, upper-camel-case name of the entity being tested (e.g., `Product`).
    - `__entityName__`: The singular, lower-camel-case name of the entity (e.g., `product`).

## Examples

### Example 1: Creating a test for `ProductService`

An agent needs to add an integration test for the recently created `ProductService`.

1.  The agent invokes this skill.
2.  It sees that `BaseIntegrationTest.kt` already exists, so it skips that step.
3.  It copies `IntegrationTest.kt.template` to `.../service/ProductServiceTest.kt`.
4.  It replaces `__EntityName__` with `Product` and `__GROUP_ID__` with the correct group ID inside the new file.
5.  The new test is now ready to be run.