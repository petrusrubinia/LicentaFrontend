package com.example.frigider.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.frigider.dataAcces.api.UserApi
import com.example.frigider.dataAcces.retrofit.RetrofitProvider
import com.example.frigider.model.User.CreateUser
import com.example.frigider.model.User.User
import kotlinx.coroutines.launch


class SettingsViewModel (application: Application): AndroidViewModel(application) {


    private val userService = RetrofitProvider.createService(application, UserApi::class.java)
    var userAccount: MutableLiveData<User?> = MutableLiveData()
    fun getUser() {
        println("vaaalll" +userAccount.value)
        userAccount.value = LoginViewModel.userAccou
        println("vaaalll" +userAccount.value)

    }
    fun saveChanges(newUserinfo: User) {
        viewModelScope.launch {
            userService.saveChanges(newUserinfo)
        }
    }

}