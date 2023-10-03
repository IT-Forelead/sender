package com.sender

import cats.effect.Async
import cats.effect.Resource
import cats.effect.std.Console
import org.typelevel.log4cats.Logger
import pureconfig.generic.auto.exportReader

case class Environment[F[_]](
    config: Config
  ) {}

object Environment {
  def make[F[_]: Async: Console: Logger]: Resource[F, Environment[F]] =
    for {
      config <- Resource.eval(ConfigLoader.load[F, Config])
      _ = Logger[F].info(s"RUNNING AS ASYNC")
    } yield Environment[F](config)
}
