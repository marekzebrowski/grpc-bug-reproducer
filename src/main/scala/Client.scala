package pl.inti
import akka.actor._
import akka.stream.scaladsl._
import akka._
import akka.util._

import scala.concurrent.duration._
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import com.typesafe.config.{ConfigFactory, ConfigValueType}

import scala.jdk.CollectionConverters._
import akka.grpc.GrpcClientSettings
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.{ActorMaterializer, Materializer}
import pl.inti.helloworld.{GreeterServiceClient, HelloRequest}

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

object Client  {
    def main(args: Array[String]): Unit = {
        implicit val system: ActorSystem = ActorSystem("HttpToGrpc")
        implicit val mat: Materializer = ActorMaterializer()
        implicit val ec: ExecutionContext = system.dispatcher

        val settings = GrpcClientSettings.connectToServiceAt("localhost",8080).withDeadline(1.seconds).withTls(false)
        val client = GreeterServiceClient(settings)
        println("Requesting hello")
        val x = client.sayHello(HelloRequest("Me")).map(reply => println("reply"))

        x.map{ _ =>

          println("DONE")
        }
    }
}