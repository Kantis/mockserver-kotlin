# mockserver-kotlin

Kotlin-style specifications of expectations for MockServer

## Project status
Proof-of-concept. No artifacts available as of now. Don't expect any support, bug fixes, improvements.

Opinionated. Responds with `200 OK` and `application/json` by default.

## Comparison


### Traditional setup
```kotlin
mockServerclient.`when`(
  HttpRequest.request()
    .withMethod("GET")
    .withPath("/api/v1/hello")
    .withQueryParameter("name", "john")
).respond(
  HttpResponse.response()
    .withStatusCode(200)
    .withReasonPhrase("OK")
    .withBody("""{ "greeting": "hello, john!" }""", MediaType.APPLICATION_JSON)
)
```


### With mockserver-kotlin :zap:

```kotlin

mockServerClient.onRequest {
  get("/api/v1/hello")
  withQueryParameter("name", "john")
}.respondWith {
  content("""{ "greeting": "hello, john!" }""")
}

```
