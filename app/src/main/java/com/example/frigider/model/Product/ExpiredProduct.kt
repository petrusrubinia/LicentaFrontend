package com.example.frigider.model.Product

import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

data class ExpiredProduct(
    val month: String,
    val year: String,
    val lista_produse: ArrayList<ProductWithId>
): Serializable