package com.example.frigider.viewModel


import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.frigider.model.Category.Category
import com.example.frigider.model.Product.CountCategory
import com.example.frigider.model.Product.ExpiredProduct
import com.example.frigider.model.Product.Product
import com.example.frigider.model.Product.ProductWithId
import com.example.frigider.repository.api.CategoryApi
import com.example.frigider.repository.api.ProductApi
import com.example.frigider.repository.retrofit.RetrofitProvider
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class InfoProductViewModel(application: Application) : AndroidViewModel(application) {

    private val infoProductService =
        RetrofitProvider.createService(application, ProductApi::class.java)
    var liveData: MutableLiveData<MutableList<ExpiredProduct>?> = MutableLiveData(null)

    @RequiresApi(Build.VERSION_CODES.O)
    fun getExpiredList() {
        viewModelScope.launch {
            var list = infoProductService.getProducts()
            val list2 = mutableListOf<ExpiredProduct>()

            list = list.sortedWith(compareBy<ProductWithId> {it.data_expirare.split("-")[1]}.thenBy{it.data_expirare.split("-")[0]}.thenBy { it.data_expirare.split("-")[2] })

            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")


            var date1 = list[0].data_expirare.split("-")
            var arrayProduct = ArrayList<ProductWithId>()

            for (i in list.indices) {
                val el= list[i].data_expirare
                val el_day_month_year = el.split("-")

                if (el_day_month_year[1] == date1[1] && el_day_month_year[2] == date1[2]) {
                    arrayProduct.add(list[i])
                } else {
                    list2.add(ExpiredProduct(date1[1],date1[2], arrayProduct))
                    date1 = list[i].data_expirare.split("-")
                    arrayProduct = ArrayList<ProductWithId>()
                    arrayProduct.add(list[i])
                }
                if(list2.size>=11)
                    break
            }
            if (arrayProduct.size != 0)
                list2.add(ExpiredProduct(date1[1],date1[2], arrayProduct))

            if (liveData.value != list2)
                liveData.value = list2
        }

    }

    fun deleteElement(get: ProductWithId) {
        println(get)
        viewModelScope.launch {
            infoProductService.removeProduct(get.id)
        }

    }
}