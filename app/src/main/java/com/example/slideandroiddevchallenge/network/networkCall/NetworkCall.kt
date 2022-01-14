package com.example.slideandroiddevchallenge.network.networkCall

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.slideandroiddevchallenge.utils.Constants
import com.example.slideandroiddevchallenge.utils.Enums
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response
import java.lang.reflect.Type
import java.net.ConnectException

object NetworkCall {

    fun <T : Any> makeCall(
        errorTypeConverter: Type,
        requestFun: suspend () -> Response<T>
    ): MutableLiveData<ServerCallBack<T>> {
        val result = MutableLiveData<ServerCallBack<T>>()
        //this is for showing loading on the screen
        result.value = ServerCallBack.loading(null)
        //this is for making the call inside CoroutineScope
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = requestFun()
                withContext(Dispatchers.Main) {
                    when {
                        response.code() == Enums.NetworkResponseCodes.SUCCESS.code -> {
                            response.body()?.let { body ->
                                result.value = ServerCallBack.success(body)
                                return@withContext
                            }
                        }
                        response.code() == Enums.NetworkResponseCodes.CREATED_201.code -> {
                            response.body()?.let { body ->
                                result.value = ServerCallBack.success(body)
                                return@withContext
                            }
                        }
                        response.code() == Enums.NetworkResponseCodes.SUCCESS_204.code -> {
                            result.value = ServerCallBack.success(null)
                            return@withContext
                        }
                        response.code() == Enums.NetworkResponseCodes.Unprocessable.code -> {
                            //TODO handle un processable error
                        }
                        response.code() == Enums.NetworkResponseCodes.UnAuthorizedUser.code -> {
                            result.value = ServerCallBack.error(Constants.UNAUTHORIZED_ERROR)
                        }
                        else -> result.value = ServerCallBack.error(Constants.GENERAL_ERROR)
                    }
                }
            } catch (e: Throwable) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    if (e is ConnectException) {
                        //this is no internet exception
                        result.value = ServerCallBack.error(Constants.NO_INTERNET)
                    } else
                        result.value = ServerCallBack.error(Constants.GENERAL_ERROR)
                }
            }
        }
        return result
    }
}