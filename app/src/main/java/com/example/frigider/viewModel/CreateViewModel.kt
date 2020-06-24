package com.example.frigider.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.frigider.dataAcces.api.UserApi
import com.example.frigider.dataAcces.retrofit.RetrofitProvider

class CreateViewModel(application: Application): AndroidViewModel(application) {


    companion object {
        var src: String? = null
    }

    private val userService = RetrofitProvider.createService(application, UserApi::class.java)
    var isLogValue: MutableLiveData<Boolean> = MutableLiveData()
}