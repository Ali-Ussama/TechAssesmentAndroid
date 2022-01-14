package com.example.slideandroiddevchallenge.network.networkCall


/*********
 * this is a callback back came from the server
 *
 * *********/
data class ServerCallBack<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T?): ServerCallBack<T>? {
            return ServerCallBack(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): ServerCallBack<T> {
            return ServerCallBack(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): ServerCallBack<T> {
            return ServerCallBack(Status.LOADING, data, null)
        }
    }
}