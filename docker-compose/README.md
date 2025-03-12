## Grafana Loki and Alloy

### Official website for Grafana ->
#### https://grafana.com/

### Official documentation link for implementing Grafana Loki using docker compose ->
#### https://grafana.com/docs/loki/latest/get-started/quick-start/

### The Docker Compose configuration runs the following components, each in its own container:
- **flog:** which generates log lines. flog is a log generator for common log formats. (Here we are using our Microservices to produce logs)
- **Grafana Alloy:** which scrapes the log lines from flog, and pushes them to Loki through the gateway.
- **Gateway (nginx):** which receives requests and redirects them to the appropriate container based on the request’s URL.
- **Loki read component:** which runs a Query Frontend and a Querier.
- **Loki write component:** which runs a Distributor and an Ingester.
- **Loki backend component:** which runs an Index Gateway, Compactor, Ruler, Bloom Planner (experimental), Bloom Builder (experimental), and Bloom Gateway (experimental).
- **Minio:** which Loki uses to store its index and chunks.
- **Grafana:** which provides visualization of the log lines captured within Loki.

-------------------------------------------------------------------------------------------------------------------------------


Link to check prometheus metrics -
http://localhost:9090/

Link to check Grafana tool -
http://localhost:3000/

Default credentials for http://localhost:3000/dashboards -
User: admin
Password: admin

