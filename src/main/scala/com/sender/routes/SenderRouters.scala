package com.sender.routes

import cats.effect.Sync
import cats.implicits.catsSyntaxApplicativeError
import cats.implicits.toFlatMapOps
import com.sender.domain.Customer
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.circe.JsonDecoder
import org.http4s.circe.toMessageSyntax
import org.http4s.dsl.Http4sDsl

final case class SenderRouters[F[_]: Sync: JsonDecoder]() extends Http4sDsl[F] {
  val routes: HttpRoutes[F] =
    HttpRoutes.of[F] {

      case req @ POST -> Root / "send" =>
        req
          .asJsonDecode[Customer]
          .attempt
          .flatMap {
            case Right(a) => Ok(a)
            case Left(_) => BadRequest("!Nice")
          }
    }
}
