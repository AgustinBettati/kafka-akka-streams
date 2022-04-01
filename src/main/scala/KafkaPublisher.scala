import KafkaPublisher.system.dispatcher
import akka.Done
import akka.actor.ActorSystem
import akka.kafka.ProducerSettings
import akka.kafka.scaladsl.Producer
import akka.stream.DelayOverflowStrategy
import akka.stream.scaladsl.Source
import com.typesafe.config.Config
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
import scala.util.Random

object KafkaPublisher extends App {

  private val topic = "sensor-data"

  implicit val system: ActorSystem = ActorSystem()
  val config = system.settings.config.getConfig("akka.kafka.producer")
  val producerSettings = ProducerSettings(config, new StringSerializer, new StringSerializer)

  val done: Future[Done] =
    Source(Stream.from(1))
      .throttle(1, 2.second)
      .map{ _ =>
        val reading = Random.nextLong(20)
        val msg = s"New reading: $reading"
        println(s"Generating new msg: $msg - topic: $topic")
        new ProducerRecord[String, String](topic, msg)
      }
      .runWith(Producer.plainSink(producerSettings))
}
