package com.example.slideandroiddevchallenge.entity.response

data class UsersResponse(
    val meta: Meta?,
    val data: ArrayList<User>?
)

data class Meta(
    val pagination: Pagination?
)

data class Pagination(
    val total: Int?,
    val pages: Int?,
    val page: Int?,
    val limit: Int?,
    val links: Links?
)

data class Links(
    val previous: String?,
    val current: String?,
    val next: String?
)

data class User(
    val id: Int?,
    val name: String?,
    val email: String?,
    val gender: String?,
    val status: String?
)