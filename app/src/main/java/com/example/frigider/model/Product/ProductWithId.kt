package com.example.frigider.model.Product

import java.io.Serializable

data class ProductWithId(
    val id: Int,
    val nume: String,
    val categorie: String,
    val cantitate: String,
    val temperatura: Int,
    val data_expirare: String,
    val data_achizitionare: String,
    val cod_de_bare: String
) : Serializable
