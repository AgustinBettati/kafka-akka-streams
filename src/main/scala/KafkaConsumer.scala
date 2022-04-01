import akka.Done
import akka.actor.ActorSystem
import akka.kafka.{ConsumerSettings, Subscriptions}
import akka.kafka.scaladsl.Consumer
import akka.stream.scaladsl.{Keep, RunnableGraph, Sink, Source}
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord}
import org.apache.kafka.common.serialization.StringDeserializer

import scala.concurrent.Future

object KafkaConsumer extends App {

  private val topic = "quickstart"

  implicit val system: ActorSystem = ActorSystem()
  val config = system.settings.config.getConfig("akka.kafka.consumer")
  val consumerSettings =
    ConsumerSettings(config, new StringDeserializer, new StringDeserializer)
      .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
      .withGroupId("consumer-group-id")

  private val streamSrc: Source[ConsumerRecord[String, String], Consumer.Control] =
    Consumer.plainSource(consumerSettings, Subscriptions.topics(topic))

  private val printingSink: Sink[ConsumerRecord[String, String], Future[Done]] = Sink.foreach {
    consumerRecord => println(s"Consumed msg: ${consumerRecord.value()} - ${consumerRecord.timestamp()}")
  }

  private val graph: RunnableGraph[Consumer.Control] = streamSrc.toMat(printingSink)(Keep.left)
  private val control: Consumer.Control = graph.run()
  Thread.sleep(20000)
}
