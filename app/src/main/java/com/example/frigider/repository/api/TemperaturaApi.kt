package com.example.frigider.repository.api

import retrofit2.http.GET

private const val optimTemp = "api/aliment/optimTemp"
private const val actualTemp = "api/aliment/actualTemp"
interface TemperaturaApi{

    @GET(optimTemp)
    suspend fun getOptimTemp(): Int

    @GET(actualTemp)
    suspend fun getActualTemp(): Int
}