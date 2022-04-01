import KafkaPublisher.system.dispatcher
import akka.Done
import akka.actor.ActorSystem
import akka.kafka.ProducerSettings
import akka.kafka.scaladsl.Producer
import akka.stream.scaladsl.Source
import com.typesafe.config.Config
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer

import scala.concurrent.Future

object KafkaPublisher extends App {

  private val topic = "quickstart"

  implicit val system: ActorSystem = ActorSystem()
  val config = system.settings.config.getConfig("akka.kafka.producer")
  val producerSettings = ProducerSettings(config, new StringSerializer, new StringSerializer)

  val done: Future[Done] =
    Source(1 to 10)
      .map(_.toString)
      .map(value => new ProducerRecord[String, String](topic, s"new-value-$value"))
      .runWith(Producer.plainSink(producerSettings))

  done.onComplete { _ =>
    system.terminate()
  }
}
