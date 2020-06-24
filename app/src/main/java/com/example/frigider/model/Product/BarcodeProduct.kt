package com.example.frigider.model.Product

import java.io.Serializable

data class BarcodeProduct(
    val cod_de_bare: String,
    val categorie: String,
    val denumire: String,
    val temperatura: Int

): Serializable