package com.sender

import cats.effect.Async
import cats.effect.Resource
import cats.effect.std.Console
import com.sender.utils.Mailer
import eu.timepit.refined.pureconfig._
import org.typelevel.log4cats.Logger
import pureconfig.generic.auto.exportReader

case class Environment[F[_]](
    config: Config,
    mailer: Mailer[F],
  ) {}

object Environment {
  def make[F[_]: Async: Console: Logger]: Resource[F, Environment[F]] =
    for {
      config <- Resource.eval(ConfigLoader.load[F, Config])
      mailer = Mailer.make(config.monitoringMailer)
      _ = Logger[F].info(s"RUNNING AS ASYNC")
    } yield Environment[F](config, mailer)
}
