---
name: feature-slice
description: Generates all necessary files for a new feature (Controller, Service, Entity, Repository) following a vertical slice pattern in a Hexagonal Architecture. Use this when adding a new CRUD-like feature to the service.
---

# Add Feature Slice

## Instructions

This skill generates the set of files required for a new feature, following the project's vertical slice and pragmatic Hexagonal Architecture pattern.

1.  **Identify File Locations**: Determine the correct location for each new file based on the architecture.
    - **Entity**: `__ARTIFACT_ID__-usecase/src/main/kotlin/__GROUP_PATH__/domain/`
    - **Repository Port**: `__ARTIFACT_ID__-usecase/src/main/kotlin/__GROUP_PATH__/repository/`
    - **Service/Use Case**: `__ARTIFACT_ID__-usecase/src/main/kotlin/__GROUP_PATH__/service/`
    - **Repository Adapter**: `__ARTIFACT_ID__-app/src/main/kotlin/__GROUP_PATH__/adapters/repository/`
    - **Controller**: `__ARTIFACT_ID__-app/src/main/kotlin/__GROUP_PATH__/infra/rest/v1/`
2.  **Instantiate Templates**: Copy the templates from the `templates` directory into the correct locations.
3.  **Rename Files**: Rename the files, replacing `__EntityName__` with the name of your feature entity (e.g., `Product`).
4.  **Replace Placeholders**: Perform a search and replace for the following placeholders in the new files:
    - `__GROUP_ID__`: The Maven group ID (e.g., `br.com.company`).
    - `__EntityName__`: The singular, upper-camel-case name of the entity (e.g., `Product`).
    - `__entityName__`: The singular, lower-camel-case name of the entity (e.g., `product`).
    - `__ENTITY_NAME_PLURAL_SNAKE_CASE__`: The plural, lower-snake-case name for use in URLs (e.g., `products`).

## Examples

### Example 1: Adding a `Product` feature

An agent is asked to "add a new feature to manage products".

1.  The agent identifies the core entity is `Product`.
2.  It invokes this skill.
3.  It copies `Entity.kt.template` to `.../domain/Product.kt`, `Controller.kt.template` to `.../infra/rest/v1/ProductController.kt`, and so on.
4.  It replaces `__EntityName__` with `Product`, `__entityName__` with `product`, and `__ENTITY_NAME_PLURAL_SNAKE_CASE__` with `products` in the content of the new files.