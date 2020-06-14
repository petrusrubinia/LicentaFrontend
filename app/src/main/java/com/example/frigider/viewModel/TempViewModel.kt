package com.example.frigider.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.frigider.dataAcces.api.TemperaturaApi
import com.example.frigider.dataAcces.retrofit.RetrofitProvider
import kotlinx.coroutines.launch


class TempViewModel(application: Application) : AndroidViewModel(application) {

    private val tempService = RetrofitProvider.createService(application, TemperaturaApi::class.java)
    var actualTemp: MutableLiveData<Int?> = MutableLiveData(null)
    var optimTemp: MutableLiveData<Int?> = MutableLiveData(null)

    fun getActualTemp() {
        viewModelScope.launch {
            var rez: Int = tempService.getActualTemp()
            if (actualTemp.value != rez)
                actualTemp.value = rez
        }

    }

    fun getOptimTemp() {

        viewModelScope.launch {
            var rez: Int = tempService.getOptimTemp()
            if (optimTemp.value != rez)
                optimTemp.value = rez
        }
    }

}