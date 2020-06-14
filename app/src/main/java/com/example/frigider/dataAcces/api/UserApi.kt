package com.example.frigider.dataAcces.api

import com.example.frigider.model.User.AuthUser
import com.example.frigider.model.User.CreateUser
import com.example.frigider.model.User.User
import retrofit2.Call
import retrofit2.http.*

private const val authApi = "api/user/audentificare"
private const val createApi = "api/user/inregistrare"
private const val saveChanges = "api/user/modificare"

interface UserApi {

    @POST(authApi)
    suspend fun authentication(@Body user: AuthUser): User

    @POST(createApi)
     suspend fun createAccount(@Body user: CreateUser): User

    @POST(saveChanges)
    suspend fun saveChanges(@Body new:User):Boolean


}