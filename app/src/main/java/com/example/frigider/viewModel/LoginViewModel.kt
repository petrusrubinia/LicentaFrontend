package com.example.frigider.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.frigider.model.User.AuthUser
import com.example.frigider.dataAcces.api.UserApi
import com.example.frigider.dataAcces.retrofit.RetrofitProvider
import java.lang.Exception
import androidx.lifecycle.viewModelScope
import com.example.frigider.model.User.CreateUser
import com.example.frigider.model.User.User
import kotlinx.coroutines.launch


class LoginViewModel(application: Application): AndroidViewModel(application) {


    companion object {
        var userAccou: User? = null
    }

    private val userService = RetrofitProvider.createService(application, UserApi::class.java)
    var isLogValue: MutableLiveData<Boolean> = MutableLiveData()
    var userAccount: MutableLiveData<User> = MutableLiveData()

    fun authentication(user: AuthUser) {
        viewModelScope.launch {
            try{
                val isLog  = userService.authentication(user)
                if(isLog.nume!="") {
                    isLogValue.value = true
                    userAccount.value = isLog
                    userAccou = isLog
                    println("uuuuurrrrrrrrrrrrluuuuu"+ userAccount.value)
                    Toast.makeText(
                        getApplication<Application>().applicationContext!!,
                        "SUCCESS",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else {
                    isLogValue.value = false
                    Toast.makeText(
                        getApplication<Application>().applicationContext!!,
                        "NOT SUCCEED",
                        Toast.LENGTH_LONG
                    ).show()
                }


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
        viewModelScope.launch{
            userAccount.value = userService.createAccount(createUser)
            userAccou = userAccount.value
        }
    }
    fun getUser(){
        println("din nouuu->>"+userAccount.value)
        userAccount.value = userAccount.value
    }

}