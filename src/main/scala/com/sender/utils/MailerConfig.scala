package com.sender.utils

import com.sender.utils.MailerConfig.SenderAndRecipients
import eu.timepit.refined.types.net.SystemPortNumber
import eu.timepit.refined.types.string.NonEmptyString

case class MailerConfig(
    enabled: Boolean,
    host: NonEmptyString,
    port: SystemPortNumber,
    username: NonEmptyString,
    password: NonEmptyString,
    fromAddress: NonEmptyString,
    recipients: List[NonEmptyString],
  ) {
  def toSenderAndRecipients: SenderAndRecipients =
    SenderAndRecipients(fromAddress, recipients)
}

object MailerConfig {
  case class SenderAndRecipients(
      fromAddress: NonEmptyString,
      recipients: List[NonEmptyString],
    )
}
