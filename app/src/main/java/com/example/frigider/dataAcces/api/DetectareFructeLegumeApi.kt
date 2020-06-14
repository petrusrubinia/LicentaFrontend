package com.example.frigider.dataAcces.api

import com.example.frigider.model.Product.Product
import com.example.frigider.model.Product.ProductWithId
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

private const val pathDetectare = "api/aliment/detectare"
interface DetectareFructeLegumeApi{

    @Multipart
    @POST(pathDetectare)
    suspend fun detect(@Part multipart: MultipartBody.Part): MutableList<ProductWithId>

}