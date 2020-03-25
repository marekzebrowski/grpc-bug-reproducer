package pl.inti
import akka.actor._
import akka.grpc.scaladsl.ServiceHandler
import akka.http.scaladsl.{Http, Http2, HttpConnectionContext}
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import pl.inti.helloworld.{GreeterService, GreeterServiceHandler, HelloReply, HelloRequest}

import scala.concurrent.{ExecutionContext, Future, Promise}

class SrvImpl(implicit  system: ActorSystem) extends GreeterService {
import system.dispatcher
  val names = Seq("Ala","Ola","Bela","Mela")
  override def sayHello(in: HelloRequest): Future[HelloReply] = {
    Future{HelloReply(in.name)}
  }

}

object Server {

  def main(args: Array[String]):Unit = {

    implicit val system = ActorSystem("X")
    implicit val mat = ActorMaterializer()
    import system.dispatcher
    val binding = Promise[Http.ServerBinding]
    def run(): Future[Http.ServerBinding] = {
      implicit val ec: ExecutionContext = system.dispatcher
      val srv = new SrvImpl
      val handler =  GreeterServiceHandler.partial(srv)
      val service = ServiceHandler.concatOrNotFound(handler)
      Http().bindAndHandleAsync(
        service,
        "0.0.0.0",
        8081, connectionContext = HttpConnectionContext())
      Http2().bindAndHandleAsync(
        service,
        "0.0.0.0",
        8080, connectionContext = HttpConnectionContext()).map { b => binding.success(b);b }
    }

    run()
  }

}
