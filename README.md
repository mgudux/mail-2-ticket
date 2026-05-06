# Mail-2-Ticket

A backend service built with Spring Boot that converts `.eml` files into support tickets. The system reads the email files, extracts the text and attachments and uses an AI model to analyze the content and route the ticket to a department. 

Here's a quick preview:
---

## Project Structure

```text
├── docker-compose.yml          Infrastructure containerization (Database/Services)[cite: 1]
├── mvnw / pom.xml              Maven build configuration and wrappers[cite: 1]
└── src/
    ├── main/java/.../mail2ticket/
    │   ├── config/             Application and attachment properties[cite: 1]
    │   ├── controller/         REST API endpoints for uploads, tickets, and customers[cite: 1]
    │   ├── domain/             Database entities, DTOs, and internal models (AiEmlAnalysis, ParsedMail)[cite: 1]
    │   ├── exception/          Error handling and custom exceptions[cite: 1]
    │   ├── mapper/             Mapping interfaces for Entity-DTO conversion[cite: 1]
    │   ├── repositories/       Spring Data database interfaces[cite: 1]
    │   └── services/           Business logic, AI integration, and file parsing[cite: 1]
    └── test/                   Unit testing suite[cite: 1]
```

**Controllers** (`controller/`), expose RESTful endpoints including `UploadController` for file uploads, alongside standard controllers for `Ticket`, `Customer`, and `EmlFile`[cite: 1].

**Services** (`services/`), contain the application logic[cite: 1]. The `EmailProcessingService` coordinates the workflow, utilizing the `EmlParserService` to read `.eml` files and the `MultimodalAiService` for analysis[cite: 1]. 

**Domain** (`domain/`), organizes data structures into `entities` (e.g., `Ticket`, `Customer`, `Department`), `dto` for API responses, and `internal` models like `AiEmlAnalysis`[cite: 1].

---

## How it works

1. A file is uploaded to the `UploadController`[cite: 1].
2. The `EmlParserService` reads the file, extracting the sender, subject, body text and isolating any `AttachmentData`[cite: 1].
3. The `ParsedMail` content is sent to the `MultimodalAiService`, which interfaces with an AI model to evaluate the email text[cite: 1]. The AI provides a summary of the entire email. The system prompt for this analysis can be modified in `AiAnalysisMapperImpl.java` at `buildPrompt()`[cite: 1].
4. The AI determines the `Sentiment` based on keywords and returns a score from 0 to 100[cite: 1]. This score can be used to determine SLAs for the new ticket. The AI also assigns the appropriate `Department`[cite: 1].
5. The `CustomerService` checks if the sender exists, creating a new `Customer` record if they dont exist[cite: 1].
6. The `TicketService` generates a new `Ticket` linking the `Customer`, `EmlFile`, `Sentiment`, and `Department` and sets the initial `TicketStatus` to `OPEN`[cite: 1].
7. The API responds with an `UploadResponse` detailing the newly created ticket, email and customer[cite: 1].

---

## Setup

### Requirements

- Java 17 or newer
- Maven (or just use the provided `./mvnw` wrapper)[cite: 1]
- Docker & Docker Compose (for local database setup)[cite: 1]
- API Key for the Multimodal AI provider (Gemini is free)

### Configuration

The application uses `application.yml` for configuration[cite: 1]. Set up your local environment variables or modify the YAML file directly:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/mail2ticket
    username: ${DB_USER}
    password: ${DB_PASS}
ai:
  provider:
    api-key: ${AI_API_KEY}
```

### Running locally

1. Start the required infrastructure using Docker[cite: 1]:
```bash
docker compose up -d
```

2. Run the Spring Boot application using the Maven wrapper or your IDE[cite: 1]:
```bash
./mvnw spring-boot:run
```

---

## Core Features

- **Email Processing**: Reads `.eml` files, extracting metadata, body text, and attachments via the `EmlParserService`[cite: 1].
- **AI Analysis**: Uses an AI model to summarize emails, assign a `Department`, and calculate a 0-100 `Sentiment` score based on keywords[cite: 1].
- **Error Handling**: Returns standard API responses using a `GlobalExceptionHandler` that manages errors like `ConflictException` and `ValidationException`[cite: 1].
- **Data Structure**: Separates database schemas from API responses using the `mapper` package to return `TicketDto` and `CustomerDto` objects[cite: 1].

---

## Planned Updates

- **Storage Service**: The `StorageService` is planned for a future update[cite: 1]. It will upload an Excel file to AWS S3 containing the Upload Response Entities.
- **Testing**: Unit tests are currently limited (`CustomerServiceImplTest.java`)[cite: 1]. More tests for the service layer will be added in a future update.

---

## Notes

- The `GlobalExceptionHandler` catches unhandled exceptions and wraps them in an `ErrorResponse` payload[cite: 1].
- The `ProcessingStatus` entity tracks the lifecycle of an uploaded file to log failures during the parsing or AI analysis phases[cite: 1].
