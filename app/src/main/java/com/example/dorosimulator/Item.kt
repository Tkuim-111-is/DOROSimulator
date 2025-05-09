package com.example.dorosimulator

import java.io.Serializable

data class Item(
    val photo: Int,
    val name: String,
    var quantity: Int
): Serializable
