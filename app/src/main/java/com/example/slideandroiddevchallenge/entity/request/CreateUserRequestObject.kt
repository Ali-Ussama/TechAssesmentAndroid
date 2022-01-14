package com.example.slideandroiddevchallenge.entity.request

data class CreateUserRequestObject(
    val name: String?,
    val email: String?,
    val gender: String?,
    val status: String?
)