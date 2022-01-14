package com.example.slideandroiddevchallenge.network.api

import com.example.slideandroiddevchallenge.entity.request.CreateUserRequestObject
import com.example.slideandroiddevchallenge.entity.response.CreateUserResponse
import com.example.slideandroiddevchallenge.entity.response.UsersResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface UserService {

    @GET("public/v1/users")
    suspend fun getUsers(): Response<UsersResponse>

    @GET("public/v1/users")
    suspend fun getUsersByPage(@Query("page") page: Int?): Response<UsersResponse>

    @POST("public/v1/users")
    suspend fun createNewUser(@Body body: CreateUserRequestObject): Response<CreateUserResponse>

    @DELETE("public/v1/users/{userId}")
    suspend fun deleteUser(@Path("userId") userId: String): Response<ResponseBody>
}