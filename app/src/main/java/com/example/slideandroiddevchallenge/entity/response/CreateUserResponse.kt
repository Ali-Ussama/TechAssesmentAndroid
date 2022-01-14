package com.example.slideandroiddevchallenge.entity.response

import com.example.slideandroiddevchallenge.entity.request.CreateUserRequestObject

data class CreateUserResponse(
    val meta: Meta?,
    val data: CreateUserRequestObject?
)

data class Validation(
    val field: String?,
    val message: String?
)