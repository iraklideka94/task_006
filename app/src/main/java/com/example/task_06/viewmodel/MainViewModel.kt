package com.example.task_06.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task_06.Resource.Resource
import com.example.task_06.Retrofit.RetrofitInstance
import com.example.task_06.model.LogIn
import com.example.task_06.model.LogInModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val _loginState = MutableStateFlow<Resource<LogIn>>(Resource.Loading(true))
    val loginState = _loginState.asStateFlow()


    fun getLogIn(email: String, password: String) {
        viewModelScope.launch {
            loginResponse(email = email, password = password).collect {
                _loginState.value = it
            }
        }
    }

    private fun loginResponse(email: String, password: String) = flow<Resource<LogIn>> {
        emit(Resource.Loading(true))
        try {
            val response = RetrofitInstance.request()
                .getLoginData(LogInModel(email = email, password = password))
            if (response.isSuccessful) {
                val body = response.body()
                emit(Resource.Success(body!!))
            } else {
                val error = response.errorBody()?.string()
                emit(Resource.Error(error!!))
            }

        } catch (e: Throwable) {
            emit(Resource.Error(e.toString()))
        }

    }

}