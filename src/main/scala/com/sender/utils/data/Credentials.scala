package com.sender.utils.data

import eu.timepit.refined.types.string.NonEmptyString

case class Credentials(
    user: NonEmptyString,
    password: NonEmptyString,
  )
