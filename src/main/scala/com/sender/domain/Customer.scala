package com.sender.domain

import io.circe.generic.JsonCodec

@JsonCodec
case class Customer(
    fullName: String,
    companyName: String,
    phone: String,
    system: String,
  )
