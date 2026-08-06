# ADR 0001: Activity → AI Recommendation flow

Status: Proposed

Context:
- Activity events are produced by `activityService` and consumed by `AI_Service` via RabbitMQ.
- Recommendations must include `analysis`, `improvements`, `suggestions`, and `safety` fields.

Decision:
- Keep RabbitMQ for async decoupling and resilience.
- AI responses must be parsed defensively; on parse failure produce a default recommendation with safety guidance.

Consequences:
- Agents and contributors must not change event names, routing keys, or recommendation schema without an ADR and tests.
