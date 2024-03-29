package com.sender.server

import scala.concurrent.duration.DurationInt
import cats.Monad
import cats.data.NonEmptyList
import cats.effect._
import cats.syntax.all._
import com.comcast.ip4s.Host
import com.comcast.ip4s.Port
import com.sender.Environment
import com.sender.Config.HttpServerConfig
import com.sender.routes.SenderRouters
import org.http4s.HttpApp
import org.http4s.HttpRoutes
import org.http4s.circe.JsonDecoder
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits._
import org.http4s.server.Server
import org.http4s.server.middleware._
import org.http4s.server.websocket.WebSocketBuilder2
import org.typelevel.ci.CIStringSyntax
import org.typelevel.log4cats.Logger

object HttpServer {
  private def allRoutes[F[_]: Sync: JsonDecoder: Logger](
      env: Environment[F]
    ): NonEmptyList[HttpRoutes[F]] =
    NonEmptyList
      .of[HttpRoutes[F]](
        SenderRouters[F](env.mailer, env.config.monitoringMailer).routes
      )

  private val CorsConfig: CORSPolicy =
    CORS
      .policy
      .withAllowOriginAll
      .withAllowMethodsAll
      .withAllowHeadersIn(
        Set(
          ci"Content-Type",
          ci"Authorization",
        )
      )
      .withMaxAge(1.day)

  private def middleware[F[_]: Monad: Temporal](http: HttpRoutes[F]): HttpRoutes[F] =
    Timeout(60.seconds)(CorsConfig(AutoSlash(http)))
  def run[F[_]: Async](
      env: Environment[F]
    )(implicit
      logger: Logger[F]
    ): Resource[F, F[ExitCode]] =
    HttpServer.make[F](env.config.httpServer, _ => allRoutes[F](env)).map { _ =>
      logger.info(s"Game Get Ez http server is started").as(ExitCode.Success)
    }

  def make[F[_]: Async: Logger](
      config: HttpServerConfig,
      routes: WebSocketBuilder2[F] => NonEmptyList[HttpRoutes[F]],
    ): Resource[F, Server] = {

    def httpApp(wsb: WebSocketBuilder2[F]): HttpApp[F] =
      middleware[F](
        routes(wsb).reduce[HttpRoutes[F]](_ <+> _)
      ).orNotFound

    EmberServerBuilder
      .default[F]
      .withHostOption(Host.fromString("0.0.0.0"))
      .withPort(
        Port
          .fromInt(config.port)
          .getOrElse(throw new IllegalArgumentException("Port is incorrect"))
      )
      .withHttpWebSocketApp(httpApp)
      .build
  }
}
