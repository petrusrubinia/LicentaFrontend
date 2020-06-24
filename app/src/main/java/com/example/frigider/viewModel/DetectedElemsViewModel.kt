package com.example.frigider.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.frigider.dataAcces.api.DetectareFructeLegumeApi
import com.example.frigider.dataAcces.api.ProductApi
import com.example.frigider.dataAcces.retrofit.RetrofitProvider
import com.example.frigider.model.Product.Product
import com.example.frigider.model.Product.ProductWithId
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.File
import java.sql.SQLOutput

class DetectedElemsViewModel(application: Application) : AndroidViewModel(application) {

    private val detectService =
        RetrofitProvider.createService(application, DetectareFructeLegumeApi::class.java)
    private val detectProductService =
        RetrofitProvider.createService(application, ProductApi::class.java)
    var detectProducts: MutableLiveData<MutableList<ProductWithId>?> = MutableLiveData(null)
    fun sendPhoto(path: String) {
        var list: MutableList<ProductWithId> = mutableListOf<ProductWithId>()
        var file: File = File(path, "")
        var requestBody: RequestBody =
            RequestBody.create(MediaType.parse("multipart/form-data"), file)
        var parts: MultipartBody.Part =
            MultipartBody.Part.createFormData("file", file.name, requestBody)
        viewModelScope.launch {
            list = detectService.detect(parts)
            if (detectProducts.value != list)
                detectProducts.value = list
        }

    }

    fun deleteElement(get: ProductWithId) {
        viewModelScope.launch {
            try {
                detectProductService.removeProduct(get.id)
            } catch (e: HttpException) {
                println("Error-Nu exista produs cu acest id")
            }

        }
    }
}