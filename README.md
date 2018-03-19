# Spring Cloud Stream Demo

* [Build the project](#build-the-project)
* [Producer Consumer Demo](#producer-consumer-demo)
* [Messaging Bridge Demo](#messaging-bridge-demo)

## Build the project

```
mvnw clean package
```

## Provision an AMQP Broker

```
cf create-service p-rabbit standard amqp
```

## Producer Consumer Demo

### Deploy

Deploy using the CF Manifest

```
$ cf push -f manifest-demo-1.yml
```

## Messaging Bridge Demo


### Deploy

Deploy using the CF Manifest

```
$ cf push -f manifest-demo-2.yml
```

### Swap the source broker to Kafka

A convenient `docker-compose.yml` is available in the `compose/docker-compose` directory. Using `docker-compose up` should get you a Kafka broker up and running in no time.

Update `SPRING_CLOUD_STREAM_KAFKA_BINDER_BROKERS` from `manifest-demo-2.yml` with the public host of your Kafka broker for the `producer` and the `bridge`.

Finally, the environment variable `SPRING_CLOUD_STREAM_BINDINGS_MAINFRAME_BINDER` from `manifest-demo-2.yml` must be changed to `kafka` for both the `producer` and the `bridge`.