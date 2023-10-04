package com.sender.utils.exception

case class InvalidAddress(cause: String) extends Throwable {
  override def getMessage: String = cause
}
