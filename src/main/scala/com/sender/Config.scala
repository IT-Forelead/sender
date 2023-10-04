package com.sender

import com.sender.utils.MailerConfig

case class Config(
    httpServer: Config.HttpServerConfig,
    monitoringMailer: MailerConfig,
  ) {
//  lazy val migrations: MigrationsConfig = MailerConfig(
//    enabled = monitoringMailer.enabled,
//    host = monitoringMailer.host,
//    port = monitoringMailer.port,
//    username = monitoringMailer.username,
//    password = monitoringMailer.password,
//    fromAddress = monitoringMailer.fromAddress,
//    recipients = monitoringMailer.recipients,
//  )

}

object Config {
//  case class MailerConfig(
//      enabled: Boolean,
//      host: NonEmptyString,
//      port: SystemPortNumber,
//      username: NonEmptyString,
//      password: NonEmptyString,
//      fromAddress: NonEmptyString,
//      recipients: List[NonEmptyString],
//    )
  final case class HttpServerConfig(port: Int)
}
