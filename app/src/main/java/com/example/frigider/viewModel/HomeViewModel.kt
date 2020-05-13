package com.example.frigider.viewModel

import android.app.Application
import android.app.PendingIntent.getActivity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.example.frigider.model.Product.CountCategory
import com.example.frigider.model.User.CreateUser
import com.example.frigider.repository.api.CategoryApi
import com.example.frigider.repository.api.ProductApi
import com.example.frigider.repository.api.UserApi
import com.example.frigider.repository.retrofit.RetrofitProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class HomeViewModel (application: Application): AndroidViewModel(application) {

    private val homeService = RetrofitProvider.createService(application, ProductApi::class.java)
    private val home2Service = RetrofitProvider.createService(application, CategoryApi::class.java)
    var liveData: MutableLiveData<MutableList<CountCategory>?> = MutableLiveData(null)

     fun getListOfCategory() {
         val listCountCategory = mutableListOf<CountCategory>()
         viewModelScope.launch {

             val list = home2Service.getAll()
             for(x in list)
             {
                 val nr = homeService.getByCategory(x.categorie)
                 listCountCategory.add(CountCategory(nr,x.categorie))
             }
            // if(liveData.value!=listCountCategory)
                liveData.value = listCountCategory
         }

     }
}


