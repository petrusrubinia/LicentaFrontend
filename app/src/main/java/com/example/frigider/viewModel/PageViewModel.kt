package com.example.frigider.viewModel


import android.app.Application
import androidx.lifecycle.*
import com.example.frigider.model.Product.ProductWithId
import com.example.frigider.dataAcces.api.ProductApi
import com.example.frigider.dataAcces.retrofit.RetrofitProvider
import com.example.frigider.model.Category.Category
import kotlinx.coroutines.launch

class PageViewModel(application: Application) : AndroidViewModel(application) {

    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = Transformations.map(_index) {
        "Hello world from section: $it"
    }

    fun setIndex(index: Int) {
        _index.value = index
    }
    private val manageService = RetrofitProvider.createService(application, ProductApi::class.java)
    private val _list = MutableLiveData<List<ProductWithId>?>()
    var liveData: LiveData<List<ProductWithId>?> = Transformations.map(_list) {
        it
    }

    fun getListOfProducts(index: Int) {
        viewModelScope.launch {
            var list = manageService.getProducts(LoginViewModel.userAccou!!.id)
            //listProduct = list
            println(index)
            when (index) {
                1 -> list = list.filter { product ->
                    product.categorie.equals("fructe") || product.categorie.equals(
                        "legume"
                    )
                }
                2 -> list = list.filter { product ->
                    product.categorie.equals("lactate") || product.categorie.equals("preparate")
                }
                3 -> list = list.filter { product ->
                    product.categorie.equals("fastfood") || product.categorie.equals("dulciuri")
                }
                4 -> list = list.filter { product -> product.categorie.equals("lichide") }
                5 -> list = list.filter { product -> product.categorie.equals("altele")}
                else -> { // Note the block
                    print("x is neither 1 nor 2")
                }
            }

            if (_list.value != list) {
                _list.value = list
            }
        }
    }

    fun deleteElement(productWithId: ProductWithId) {
        viewModelScope.launch {
            manageService.removeProduct(productWithId.id)
        }

    }
}
