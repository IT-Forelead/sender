package com.sender

case class Config(
    httpServer: Config.HttpServerConfig
  )

object Config {
  final case class HttpServerConfig(port: Int)
}
