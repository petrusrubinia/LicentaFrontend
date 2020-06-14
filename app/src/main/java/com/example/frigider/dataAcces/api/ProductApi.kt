package com.example.frigider.dataAcces.api

import com.example.frigider.model.Category.UserCategory
import com.example.frigider.model.Product.BarcodeProduct
import com.example.frigider.model.Product.CountCategory
import com.example.frigider.model.Product.Product
import com.example.frigider.model.Product.ProductWithId
import retrofit2.http.*

private const val addProduct = "api/aliment/add"
private const val updateProduct = "api/aliment/update"
private const val getAll = "api/aliment/all/{id}"
private const val removeProduct = "api/aliment/remove/{id}"
private const val countByType = "api/aliment/count"
private const val getOne = "api/aliment/getOne/{id}"
private const val getAllCategory = "api/aliment/getAllCategory"
private const val getProductByBarcode = "api/aliment/getByBarcode/{id}"


interface ProductApi {

    @POST(addProduct)
    suspend fun addProduct(@Body product: Product): Product

    @POST(updateProduct)
    suspend fun update(@Body product: ProductWithId): Product

    @GET(getAll)
    suspend fun getProducts(@Path("id") id:Int): List<ProductWithId>

    @DELETE(removeProduct)
    suspend fun removeProduct(@Path("id") id: Int)

    @POST(countByType)
     suspend fun getByCategory(@Body categorie: UserCategory) :Int


    @GET(getOne)
    suspend fun getOne(@Path("id") id: Int)

    @GET(getAllCategory)
     suspend fun getAllCategory(): List<String>

    @GET(getProductByBarcode)
    suspend fun getProductByBarcode(@Path("id") barcode: String): BarcodeProduct


}