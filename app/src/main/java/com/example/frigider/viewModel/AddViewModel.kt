package com.example.frigider.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.frigider.model.Product.Product
import com.example.frigider.model.Product.ProductWithId
import com.example.frigider.dataAcces.api.CategoryApi
import com.example.frigider.dataAcces.api.DetectareFructeLegumeApi
import com.example.frigider.dataAcces.api.ProductApi
import com.example.frigider.dataAcces.retrofit.RetrofitProvider
import com.example.frigider.model.Product.BarcodeProduct
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class AddViewModel (application: Application): AndroidViewModel(application) {

    private val addService = RetrofitProvider.createService(application, CategoryApi::class.java)
    private val add2Service = RetrofitProvider.createService(application, ProductApi::class.java)
    private val detectService = RetrofitProvider.createService(application, DetectareFructeLegumeApi::class.java)
    var liveData: MutableLiveData<List<String>?> = MutableLiveData(null)
    var detectProducts: MutableLiveData<List<Product>?> = MutableLiveData(null)
    var save: MutableLiveData<Boolean> = MutableLiveData()
    var update : MutableLiveData<Boolean> = MutableLiveData()
    var barcodeProduct : MutableLiveData<BarcodeProduct> = MutableLiveData()
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
            var product = add2Service.addProduct(produs)
            save.value = product != null
        }

    }

    fun update(produs: ProductWithId) {
        viewModelScope.launch {
            var product = add2Service.update(produs)
            update.value = product != null

        }

    }

    fun sendPhoto(path: String){
        var list:List<Product> = mutableListOf<Product>()
        var file: File = File(path,"")
        var requestBody: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file)
        var parts: MultipartBody.Part = MultipartBody.Part.createFormData("file", file.name, requestBody)
        viewModelScope.launch {
            //list = detectService.detect(parts)
            if(detectProducts.value != list)
                detectProducts.value = list
        }

    }

    fun getProductByBarcode(barcode: String) {
        viewModelScope.launch {
            //list = detectService.detect(parts)
            var barcodePr: BarcodeProduct = add2Service.getProductByBarcode(barcode)
            if(barcodeProduct.value != barcodePr)
                barcodeProduct.value = barcodePr
        }
    }
}