package com.example.frigider.dataAcces.api

import com.example.frigider.model.Category.Category
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


private const val add = "api/categorii/add"
private const val getAll = "api/categorii/all"
private const val remove = "api/categorii/remove/{id}"


interface CategoryApi {

    @POST(add)
    suspend fun addCategory(@Body category: Category): Category

    @GET(getAll)
    suspend fun getAll(): List<Category>

    @GET(remove)
    suspend fun removeCategory(@Path("id") id: Int)

}