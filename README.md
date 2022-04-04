## Alpakka Kafka example consuming and publishing to Kafka
Simple playground for starting up a kafka broker with docker and using stream processing to consume and publish messages with [alpakka-kafka](https://doc.akka.io/docs/alpakka-kafka). 
### How to run
- ```docker-compose up -d``` to start up kafka broker under `localhost:9092`
- Run `KafkaPublisher` to publish random messages every 1 second to `sensor-data` topic.
- Run `KafkaConsumer` to consume and print the value of all messages from `sensor-data` topic.