## API Key Management

API key management is crucial for securing your application's endpoints. In this project, API keys are used to control access to certain resources, ensuring that only authorized requests can interact with the endpoints.

### Features

1. Book Management:
    - Create, update, delete, and find books.
2. API Key Management:
    - Generate and save API keys.
    - Validate API keys for accessing protected resources.

### API Endpoints
1. Book Management
    - Create a Book
        
        - Endpoint: /api/v1/books
        - Method: POST
        - Request Body:

    ```json
    {
      "title": "Book Title",
      "writer": "Author Name"
    }
    ```

    - Update a Book

        - Endpoint: /api/v1/books/{id}
        - Method: PUT
        - Request Body:

    ```json
    {
      "title": "Updated Title",
      "writer": "Updated Author"
    }
    ```

    - Delete a Book

        - Endpoint: /api/v1/books/{id}
        - Method: DELETE

    - Find a Book by ID

        - Endpoint: /api/v1/books/{id}
        - Method: GET

2. API Key Management

    - Generate API Key
        - Endpoint: /api/v1/admin/generate-api-key
        - Method: POST

### Security
To protect your API endpoints, the **ApiKeyFilter** is used to validate incoming requests.

#### How it works
The filter intercepts incoming HTTP requests.
It checks the request URI to determine if the endpoint requires API key validation.

For requests that do require validation, the filter checks for the presence of an api-key header.
If the API key is missing or invalid, the filter responds with a 403 Forbidden status and an error message.

If the API key is valid, the request proceeds to the intended endpoint.

### Testing Result

#### If the api-key is invalid

![invalid.png](/Week%2008/Lecture%2015/Assignment2/assignment2/img/Screen%20Shot%202024-07-31%20at%2009.02.43.png)

#### If the api-key is valid

![valid.png](/Week%2008/Lecture%2015/Assignment2/assignment2/img/Screen%20Shot%202024-07-31%20at%2009.03.49.png)

#### Show return heade

![header.png](/Week%2008/Lecture%2015/Assignment2/assignment2/img/Screen%20Shot%202024-07-31%20at%2009.03.57.png)
