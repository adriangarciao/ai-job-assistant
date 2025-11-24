# AI Job Assistant

![CI](https://github.com/adriangarciao/ai-job-assistant/actions/workflows/maven.yml/badge.svg?branch=main)

Small Spring Boot application that provides resume/application management and an experimental AI analysis feature (parser + LLM abstraction).

Getting started

- Build and run tests locally:

```powershell
cd C:\Users\garci\dev\ai-job-app-assistant
if (Test-Path .\mvnw.cmd) { & .\mvnw.cmd test } else { mvn test }
```

- Important notes:
  - The project uses Java 21.
  - Tests run a local in-memory slice; some integration tests expect a local PostgreSQL instance only when running the full Spring context.

Contributing

- Open a PR; CI runs `mvn clean test` on push and PRs to `main`.
