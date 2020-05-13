package com.example.frigider.viewModel

import android.app.Application
import android.graphics.BitmapFactory
import androidx.lifecycle.*
import com.example.frigider.model.Category.Category
import com.example.frigider.model.Product.CountCategory
import com.example.frigider.model.Product.Product
import com.example.frigider.model.Product.ProductWithId
import com.example.frigider.repository.api.CategoryApi
import com.example.frigider.repository.api.DetectareFructeLegumeApi
import com.example.frigider.repository.api.ProductApi
import com.example.frigider.repository.retrofit.RetrofitProvider
import kotlinx.android.synthetic.main.nav_header_navigation.*
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException

class AddViewModel (application: Application): AndroidViewModel(application) {

    private val addService = RetrofitProvider.createService(application, CategoryApi::class.java)
    private val add2Service = RetrofitProvider.createService(application, ProductApi::class.java)
    private val detectService = RetrofitProvider.createService(application, DetectareFructeLegumeApi::class.java)
    var liveData: MutableLiveData<List<String>?> = MutableLiveData(null)
    companion object {
        var scannerBarcode: String? = null
    }

    fun getCategory() {
        viewModelScope.launch {
            val list = addService.getAll()
            val list2 = mutableListOf<String>()

            for (i in list.indices )
                list2.add(list[i].categorie)
            if(liveData.value != list2)
                liveData.value = list2
        }

    }

    fun save(produs: Product) {
        viewModelScope.launch {
            add2Service.addProduct(produs)
        }

    }

    fun update(produs: ProductWithId) {
        viewModelScope.launch {
            add2Service.update(produs)
        }

    }

    fun sendPhoto(path: String){
        var file: File = File(path,"")
        var requestBody: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file)
        var parts: MultipartBody.Part = MultipartBody.Part.createFormData("file", file.name, requestBody)
        viewModelScope.launch {
            detectService.detect(parts)
        }

    }
}