package com.example.task_06.Retrofit

import com.example.task_06.model.LogIn
import com.example.task_06.model.LogInModel
import com.example.task_06.model.Registration
import com.example.task_06.model.RegistrationModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiClient {
    @POST("login")
    suspend fun getLoginData(@Body userData: LogInModel): Response<LogIn>

    @POST("register")
    suspend fun getRegData(@Body userData: RegistrationModel): Response<Registration>
}