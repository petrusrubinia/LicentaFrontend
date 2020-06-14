package com.example.frigider.viewModel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.frigider.model.Product.CountCategory
import com.example.frigider.dataAcces.api.CategoryApi
import com.example.frigider.dataAcces.api.ProductApi
import com.example.frigider.dataAcces.retrofit.RetrofitProvider
import com.example.frigider.model.Category.Category
import com.example.frigider.model.Category.UserCategory
import com.example.frigider.model.Product.Product
import com.example.frigider.model.Product.ProductWithId
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class HomeViewModel (application: Application): AndroidViewModel(application) {


    private val homeService = RetrofitProvider.createService(application, ProductApi::class.java)
    private val home2Service = RetrofitProvider.createService(application, CategoryApi::class.java)
    var liveData: MutableLiveData<MutableList<CountCategory>?> = MutableLiveData(null)
    var expiredProducts: MutableLiveData<MutableList<String>?> = MutableLiveData(null)

     fun getListOfCategory() {
         println("APLEEELATAAAA")
         val listCountCategory = mutableListOf<CountCategory>()
         viewModelScope.launch {

             val list = home2Service.getAll()
             for(x in list)
             {
                 println(LoginViewModel.userAccou!!.id)
                 println(x.categorie)
                 val nr = homeService.getByCategory(UserCategory(LoginViewModel.userAccou!!.id,x.categorie))
                 listCountCategory.add(CountCategory(nr,x.categorie))
             }
             if(liveData.value!=listCountCategory) {
                 println("DIIIIIIIIIIIIIIIIIIINNNNNNN NOOOOOOOOUUU")
                 liveData.value = listCountCategory
             }
         }

     }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getExpiredProduct(){
        viewModelScope.launch {
             val pattern = "dd-MM-yyyy"
            var dateInString = SimpleDateFormat(pattern).format(Date())
            var list: List<ProductWithId> = homeService.getProducts(LoginViewModel.userAccou!!.id)
            val listExpired = mutableListOf<String>()
            var data_exp :List<String>
            var data_actuala :List<String> = dateInString.split("-")
            for (x in list) {
                data_exp = x.data_expirare.split("-")
                if (data_exp[1] == data_actuala[1] && Math.abs(data_exp[0].toInt() - data_actuala[0].toInt()) <=7)
                    listExpired.add("Produsul: "+ x.nume +" expira in "+  Math.abs(data_exp[0].toInt() - data_actuala[0].toInt())+" zile")
                else if(data_exp[1].toInt() == (data_actuala[1].toInt() + 1) && ((31 - data_actuala[0].toInt()+data_exp[0].toInt())<= 7 ))
                    listExpired.add("Produsul: "+ x.nume +" expira in "+(31 - data_actuala[0].toInt()+data_exp[0].toInt())+" zile")
            }
            if(expiredProducts.value != listExpired)
                expiredProducts.value = listExpired

        }
    }
}


