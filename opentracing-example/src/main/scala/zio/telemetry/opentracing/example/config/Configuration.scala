package zio.telemetry.opentracing.example.config

import zio.config._
import zio.config.typesafe._
import zio.config.magnolia.DeriveConfigDescriptor._
import zio.Task
import zio.ZLayer
import zio.ZIO

object Configuration {

  trait Service {
    val load: Task[Config]
  }

  object Live extends Service {
    val load: Task[Config] = for {
      source <- ZIO.fromEither(TypesafeConfigSource
        .fromHoconFile(new java.io.File("opentracing-example/src/main/resources/application.conf")))
      config <- ZIO.fromEither(read(descriptor[Config] from source))
    } yield config
  }

  val live: ZLayer[Any, Throwable, Configuration] = ZLayer.succeed(Live)

  val load: ZIO[Configuration, Throwable, Config] = ZIO.accessM[Configuration](_.get.load)
}
