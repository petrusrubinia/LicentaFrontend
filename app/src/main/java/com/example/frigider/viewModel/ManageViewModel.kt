package com.example.frigider.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.frigider.model.Product.Product
import com.example.frigider.model.Product.ProductWithId
import com.example.frigider.repository.api.ProductApi
import com.example.frigider.repository.retrofit.RetrofitProvider
import kotlinx.coroutines.launch


class ManageViewModel (application: Application): AndroidViewModel(application) {

    private val manageService = RetrofitProvider.createService(application, ProductApi::class.java)
    var liveData: MutableLiveData<List<ProductWithId>?> = MutableLiveData(null)

    fun getListOfProducts() {
        viewModelScope.launch {
            //println("aaaaaaaaaaaaaaaaaaaaaadddddddddddddd viiiiiiiiiieeeewwwwwwwww moooooooodeeeeeeell")
            val list = manageService.getProducts()
            liveData.value = list
        }

    }
}