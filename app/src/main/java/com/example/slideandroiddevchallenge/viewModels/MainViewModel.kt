package com.example.slideandroiddevchallenge.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.slideandroiddevchallenge.entity.request.CreateUserRequestObject
import com.example.slideandroiddevchallenge.repos.UserRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {


    fun getUsersMeta() = UserRepository.getUsersMeta()

    fun getUsersByPage(page: Int?) = UserRepository.getUsersByPage(page)

    fun deleteUser(userId:Int?) = UserRepository.deleteUser(userId?.toString().orEmpty())

    fun createNewUser(name: String, email: String) = UserRepository.createNewUser(
        body = CreateUserRequestObject(
            name = name,
            email = email,
            gender = "Male",
            status = "Active"
        )
    )
}