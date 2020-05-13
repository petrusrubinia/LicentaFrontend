package com.example.frigider.repository.api

import com.example.frigider.model.Product.Product
import com.example.frigider.model.Product.ProductWithId
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

private const val addProduct = "api/aliment/add"
private const val updateProduct = "api/aliment/update"
private const val getAll = "api/aliment/all"
private const val removeProduct = "api/aliment/remove/{id}"
private const val countByType = "api/aliment/count/{categorie}"
private const val getOne = "api/aliment/getOne/{id}"
private const val getAllCategory = "api/aliment/getAllCategory"


interface ProductApi {

    @POST(addProduct)
    suspend fun addProduct(@Body product: Product): Product

    @POST(updateProduct)
    suspend fun update(@Body product: ProductWithId): Product

    @GET(getAll)
    suspend fun getProducts(): List<ProductWithId>

    @DELETE(removeProduct)
    suspend fun removeProduct(@Path("id") id: Int)

    @GET(countByType)
     suspend fun getByCategory(@Path("categorie") categorie: String) :Int


    @GET(getOne)
    suspend fun getOne(@Path("id") id: Int)

    @GET(getAllCategory)
     suspend fun getAllCategory(): List<String>



}