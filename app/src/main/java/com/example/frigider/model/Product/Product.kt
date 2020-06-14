package com.example.frigider.model.Product

import java.io.Serializable
import java.util.*

//nume,categorie,cantitate,temperatura,data_expirare,data_achizitionare,cod_de_bare
data class Product(
    var idUser:Int,
    val cod_de_bare: String,
    val nume: String,
    val categorie: String,
    val cantitate: String,
    val temperatura: Int,
    val data_expirare: String,
    val data_achizitionare: String
)


