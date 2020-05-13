package com.example.frigider.model.Product

import java.util.*

//nume,categorie,cantitate,temperatura,data_expirare,data_achizitionare,cod_de_bare
data class Product(
    val nume: String,
    val categorie: String,
    val cantitate: String,
    val temperatura: Int,
    val data_expirare: String,
    val data_achizitionare: String,
    val cod_de_bare: String
)


