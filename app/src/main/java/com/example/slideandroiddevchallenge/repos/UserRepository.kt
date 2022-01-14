package com.example.slideandroiddevchallenge.repos

import com.example.slideandroiddevchallenge.entity.request.CreateUserRequestObject
import com.example.slideandroiddevchallenge.entity.response.CreateUserResponse
import com.example.slideandroiddevchallenge.entity.response.UsersResponse
import com.example.slideandroiddevchallenge.network.api.UserService
import com.example.slideandroiddevchallenge.network.networkCall.NetworkCall
import com.example.slideandroiddevchallenge.network.retrofit.ServiceFactory

object UserRepository {

    fun getUsersMeta() = NetworkCall.makeCall(UsersResponse::class.java) {
        ServiceFactory.create(UserService::class.java).getUsers()
    }

    fun getUsersByPage(page: Int?) = NetworkCall.makeCall(UsersResponse::class.java) {
        ServiceFactory.create(UserService::class.java).getUsersByPage(page = page)
    }

    fun createNewUser(body:CreateUserRequestObject) = NetworkCall.makeCall(CreateUserResponse::class.java) {
        ServiceFactory.create(UserService::class.java).createNewUser(body = body)
    }

    fun deleteUser(userId:String) = NetworkCall.makeCall(Any::class.java){
        ServiceFactory.create(UserService::class.java).deleteUser(userId = userId)
    }
}