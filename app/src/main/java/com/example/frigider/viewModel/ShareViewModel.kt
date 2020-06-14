package com.example.frigider.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.frigider.dataAcces.api.ProductApi
import com.example.frigider.dataAcces.api.UserApi
import com.example.frigider.dataAcces.retrofit.RetrofitProvider
import com.example.frigider.model.Product.ProductWithId
import com.example.frigider.model.User.User
import kotlinx.coroutines.launch

class ShareViewModel (application: Application): AndroidViewModel(application) {

//    private val userService = RetrofitProvider.createService(application, UserApi::class.java)
    private val shareService = RetrofitProvider.createService(application, ProductApi::class.java)

    var list: MutableLiveData<List<ProductWithId>?> = MutableLiveData()
    fun getList() {
        viewModelScope.launch {
            var list2 = shareService.getProducts(LoginViewModel.userAccou!!.id)
            if(list2 != list.value)
                list.value = list2
        }

    }
}