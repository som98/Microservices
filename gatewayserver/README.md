## Spring Cloud Gateway Filters

### 1. RequestTraceFilter:
- This class implements the `GlobalFilter` interface, which means it's part of the Spring Cloud Gateway filter chain.
- The purpose of this filter is to handle request tracing.
- When a request comes in, it checks if a correlation ID (e.g., `eazyBank-correlation-id`) is present in the request headers.
- If the correlation ID is present, it logs it.
- If not, it generates a new correlation ID using `java.util.UUID.randomUUID().toString()` and sets it in the request headers.
- Finally, it passes the request along the filter chain using `chain.filter(exchange)`.

### 2. FilterUtility:
- This class provides utility methods related to filters.
- It defines a constant `CORRELATION_ID` representing the header key for the correlation ID.
- The `getCorrelationId(HttpHeaders requestHeaders)` method retrieves the correlation ID from the request headers.
- The `setRequestHeader(ServerWebExchange exchange, String name, String value)` method sets a custom header in the request.
- The `setCorrelationId(ServerWebExchange exchange, String correlationId)` method sets the correlation ID in the request headers.

### 3. ResponseTraceFilter:
- This configuration class sets up a filter to handle response tracing.
- It logs the correlation ID from the request headers and adds it to the outbound response headers.



#### Overall, these filters help manage correlation IDs for tracing purposes in a Spring Cloud Gateway application.
#### They ensure that requests are associated with a unique identifier, making it easier to track and analyze them across different services or components

--------------------------------------------------------------------------------------------------------------------------------------------------------------------

#### https://docs.spring.io/spring-cloud-gateway/reference/

--------------------------------------------------------------------------------------------------------------------------------------------------------------------

### Official documentation link for Implementing Circuit Breaker ->
#### https://resilience4j.readme.io/docs/circuitbreaker
#### https://docs.spring.io/spring-cloud-circuitbreaker/reference/
