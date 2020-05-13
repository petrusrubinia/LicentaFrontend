package com.example.frigider.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.frigider.model.User.AuthUser
import com.example.frigider.repository.api.UserApi
import com.example.frigider.repository.retrofit.RetrofitProvider
import java.lang.Exception
import androidx.lifecycle.viewModelScope
import com.example.frigider.model.User.CreateUser
import kotlinx.coroutines.launch


class LoginViewModel(application: Application): AndroidViewModel(application) {


    companion object {
        var src: String? = null
    }

    private val userService = RetrofitProvider.createService(application, UserApi::class.java)
    var isLogValue: MutableLiveData<Boolean> = MutableLiveData()

    fun authentication(user: AuthUser) {
        viewModelScope.launch {
            try{
                val isLog  = userService.authentication(user)
                if(isLog) {
                    Toast.makeText(
                        getApplication<Application>().applicationContext!!,
                        "SUCCESS",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else {
                    Toast.makeText(
                        getApplication<Application>().applicationContext!!,
                        "NOT SUCCEED",
                        Toast.LENGTH_LONG
                    ).show()
                }
                isLogValue.value = isLog

            }catch (e: Exception)
            {
                Toast.makeText(
                    getApplication<Application>().applicationContext!!,
                    "NOT SUCCEED",
                    Toast.LENGTH_LONG
                ).show()

            }
        }

    }

    fun createAccount(createUser: CreateUser) {

    }
}