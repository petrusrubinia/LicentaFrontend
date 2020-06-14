package com.example.frigider.model.Product

import java.io.Serializable

data class ProductWithId(
    var id:Int,
    var idUser:Int,
    val cod_de_bare: String,
    val nume: String,
    val categorie: String,
    val cantitate: String,
    val temperatura: Int,
    val data_expirare: String,
    val data_achizitionare: String
) : Serializable
