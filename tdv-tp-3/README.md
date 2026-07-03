# TDV TP3 - GitHub REST API

## Requisitos
- Java 11+, Maven, Chrome

## Setup
```bash
echo "GITHUB_TOKEN=ghp_tu_token" > .env
mvn clean test
```

## Tests por inciso

| # | Test | Endpoint | Verifica |
|---|---|---|---|
| 1 | `testAuth` | `GET /user` | Token válido, login del usuario |
| 2 | `testListRepos` | `GET /user/repos` | Lista con nombre, visibilidad, URL |
| 3 | `testCreateRepo` | `POST /user/repos` | Crea `tdv-tp3-test` público |
| 4 | `testCreatePR` | Git API + `POST /pulls` | PR de `feature/tp3` a `main` |
| 5 | `testCommentPR` | `POST /issues/{n}/comments` | Comentario en el PR creado |

## Documentación (OpenAPI 3.0)
`openapi.yaml` documenta los 5 endpoints con auth Bearer, schemas request/response, parámetros path y body.

## SwaggerUI
Los tests levantan automáticamente un servidor HTTP embebido en puerto 8765 que sirve `swagger-ui.html` + `openapi.yaml`. Selenium abre SwaggerUI en Chrome y verifica su carga antes de ejecutar las pruebas.

Para ver la documentación sin tests (desde la carpeta del proyecto):
```bash
mvn compile -q
java -cp target/classes tdv.SwaggerServer
# Abrir http://localhost:8765
# Ctrl+C para detener
```
