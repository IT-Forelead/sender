package com.sender.routes

import cats.data.NonEmptyList
import cats.effect.Sync
import cats.implicits.catsSyntaxApplicativeError
import cats.implicits.catsSyntaxOptionId
import cats.implicits.toFlatMapOps
import com.sender.domain.Customer
import com.sender.utils._
import com.sender.utils.data.Content
import com.sender.utils.data.Email
import com.sender.utils.data.Text
import eu.timepit.refined.types.string.NonEmptyString
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.circe.JsonDecoder
import org.http4s.circe.toMessageSyntax
import org.http4s.dsl.Http4sDsl

final case class SenderRouters[F[_]: Sync: JsonDecoder](
    mailer: Mailer[F],
    config: MailerConfig,
  ) extends Http4sDsl[F] {
  val routes: HttpRoutes[F] =
    HttpRoutes.of[F] {

      case req @ POST -> Root / "send" =>
        req
          .asJsonDecode[Customer]
          .attempt
          .flatMap {
            case Right(customer) =>
              val email = Email(
                from = config.username,
                subject = NonEmptyString.unsafeFrom("Demo olish so'rovi"),
                content = Content(
                  text = Text(
                    NonEmptyString.unsafeFrom(
                      s"${customer.fullName} ismli mijoz ${customer.companyName} kompaniyasi uchun ${customer.system}" +
                        s" platformasidan demo versiya olmoqchi. Aloqa uchun telefon raqami ${customer.phone}"
                    )
                  ).some
                ),
                to = NonEmptyList.fromListUnsafe(config.recipients),
              )
              mailer
                .send(email)
                .flatMap(_ => Ok("Email notification has been sent!"))
            case Left(_) => BadRequest("Email notification has been not sent!")
          }
    }
}
