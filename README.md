# Missing traceId for RabbitMQ messages
The purpose of this project is to illustrate that spring-cloud-sleuth is missing a traceId on logs when
an `AmqpRejectAndDontRequeueException` is thrown from a method annotated with `@RabbitListener`.

## Running

Start a RabbitMQ instance by executing
```bash
docker-compose up
```

Start the Spring Boot Application with
```bash
./gradlew bootRun
```

When the service is running it will produce a message every 10 seconds that when consumed will throw an `
AmqpRejectAndDontRequeueException`.

## Expected behaviour
The log row `s.a.r.l.ConditionalRejectingErrorHandler : Execution of Rabbit message listener failed.` should contain
the same traceId as the producer and consumer log rows.

## Actual behaviour
All traceId information is missing when the ConditionalRejectingErrorHandler are reached.

```bash
2020-06-01 14:46:30.331  WARN [,,,] 10717 --- [ntContainer#0-1] s.a.r.l.ConditionalRejectingErrorHandler : Execution of Rabbit message listener failed.
```
