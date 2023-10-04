package com.sender.utils.data

import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

import eu.timepit.refined.types.string.NonEmptyString

case class Text(
    value: NonEmptyString,
    charset: Charset = StandardCharsets.UTF_8,
    subtype: String = "plain",
    headers: List[Header] = Nil,
  )
