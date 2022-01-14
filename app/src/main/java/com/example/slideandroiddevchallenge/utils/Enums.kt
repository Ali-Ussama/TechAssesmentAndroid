package com.example.slideandroiddevchallenge.utils

object Enums {

    enum class NetworkResponseCodes(val code: Int) {
        UnAuthorizedUser(401),
        SUCCESS(200),
        CREATED_201(201),
        SUCCESS_204(204),
        Unprocessable(422)
    }

}