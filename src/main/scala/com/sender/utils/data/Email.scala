package com.sender.utils.data

import cats.data.NonEmptyList
import eu.timepit.refined.types.string.NonEmptyString

/** Represents the e-mail message itself.
  *
  * @param from
  *   e-mail sender address
  * @param subject
  *   e-mail subject text
  * @param content
  *   e-mail content,
  * @param to
  *   set of e-mail receiver addresses
  * @param cc
  *   set of e-mail ''carbon copy'' receiver addresses
  * @param bcc
  *   set of e-mail ''blind carbon copy'' receiver addresses
  * @param replyTo
  *   addresses used to reply this message
  */
case class Email(
    from: NonEmptyString,
    subject: NonEmptyString,
    content: Content,
    to: NonEmptyList[NonEmptyString],
    cc: List[NonEmptyString] = Nil,
    bcc: List[NonEmptyString] = Nil,
    replyTo: List[NonEmptyString] = Nil,
    headers: List[Header] = Nil,
  )
