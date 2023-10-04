package com.sender.utils.data

import scala.concurrent.duration.DurationInt
import scala.concurrent.duration.FiniteDuration

import com.sender.utils.data.Props._
import eu.timepit.refined.types.net.SystemPortNumber
import eu.timepit.refined.types.string.NonEmptyString

final case class Props(values: Map[String, String]) {
  def withSmtpAddress(host: NonEmptyString, port: SystemPortNumber): Props =
    copy(values = values ++ Map(SmtpHostKey -> host.value, SmtpPortKey -> port.value.toString))

  def setConnectionTimeout(timeout: FiniteDuration): Props =
    copy(values = values ++ Map(SmtpConnectionTimeoutKey -> timeout.toMillis.toString))

  def setSmtpTimeout(timeout: FiniteDuration): Props =
    copy(values = values ++ Map(SmtpTimeoutKey -> timeout.toMillis.toString))

  def withTls(enable: Boolean = true, required: Boolean = false): Props =
    copy(values =
      values ++ Map(
        SmtpStartTlsEnableKey -> enable.toString,
        SmtpStartTlsRequiredKey -> required.toString,
      )
    )

  def setProtocol(protocol: String): Props =
    copy(values = values ++ Map(TransportProtocolKey -> protocol))

  def withDebug(debug: Boolean = false): Props =
    copy(values = values ++ Map(DebugKey -> debug.toString))

  def withAuth(enable: Boolean = true): Props =
    copy(values = values ++ Map(SmtpAuthKey -> enable.toString))

  def set(key: String, value: String): Props =
    copy(values = values ++ Map(key -> value))
}

object Props {
  private[sender] val DebugKey = "mail.debug"
  private[sender] val SmtpConnectionTimeoutKey = "mail.smtp.connectiontimeout"
  private[sender] val SmtpHostKey = "mail.smtp.host"
  private[sender] val SmtpPortKey = "mail.smtp.port"
  private[sender] val SmtpStartTlsEnableKey = "mail.smtp.starttls.enable"
  private[sender] val SmtpSslProtocolKey = "mail.smtp.ssl.protocols"
  private[sender] val SmtpStartTlsRequiredKey = "mail.smtp.starttls.required"
  private[sender] val SmtpTimeoutKey = "mail.smtp.timeout"
  private[sender] val TransportProtocolKey = "mail.transport.protocol"
  private[sender] val SmtpAuthKey = "mail.smtp.auth"
  private[sender] val defaultProps =
    Map(
      SmtpHostKey -> "localhost",
      SmtpPortKey -> "25",
      DebugKey -> "false",
      SmtpConnectionTimeoutKey -> 3.seconds.toMillis.toString,
      SmtpTimeoutKey -> 30.seconds.toMillis.toString,
      SmtpStartTlsEnableKey -> "true",
      SmtpSslProtocolKey -> "TLSv1.2",
      SmtpStartTlsRequiredKey -> "true",
      SmtpStartTlsRequiredKey -> "true",
      TransportProtocolKey -> "smtp",
      SmtpAuthKey -> "true",
    )

  def default: Props = Props(defaultProps)
}
