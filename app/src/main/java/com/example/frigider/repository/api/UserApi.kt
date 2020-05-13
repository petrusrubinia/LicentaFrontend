package com.example.frigider.repository.api

import com.example.frigider.model.User.AuthUser
import com.example.frigider.model.User.CreateUser
import com.example.frigider.model.User.User
import retrofit2.Call
import retrofit2.http.*

private const val authApi = "api/user/audentificare"
private const val createApi = "api/user/inregistrare"

interface UserApi {

    @POST(authApi)
    suspend fun authentication(@Body user: AuthUser): Boolean

    @POST(createApi)
     suspend fun createAccount(@Body user: CreateUser): Call<User>
}