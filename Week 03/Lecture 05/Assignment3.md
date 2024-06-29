<h2>Comparison to the best practice implementation of REST API</h2>

The key takeaways for designing high-quality REST APIs include consistency in adhering to web standards (JSON and HTTP status codes), considering performance by limiting the amount of data returned, implementing caching to reduce frequent data queries, and maintaining consistent endpoint naming.

Based on my implementation in Assignment 2, here is a comparison and areas for improvement to achieve best practice in REST API implementation:

### Accept and response with JSON

TThe APIs in the Employee app follow to the standard practice of accepting and responding with JSON. For certain operations like mapping with POST and PUT requests, users are required to include a RequestBody in JSON format.

### Use nouns instead of verbs in endpoint paths

Endpoint paths such as `"/api/v1/employee"` follow the guideline of using nouns (employee). However, there are instances such as `"upload"` where I have not yet determined an appropriate noun replacement.

### Use logical nesting on endpoints

This is an area that requires improvement. In my Assignment 2 implementation, endpoint nesting is inconsistent. For example, retrieving data for a single employee uses `"/{id}"`, but updating an employee's data uses `"/update/{id}"`. It would be more coherent to have the update endpoint as `"{id}/update"`. This same consideration applies to the delete operation as well.

