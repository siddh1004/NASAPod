package com.obvious.nasapod

import java.io.Serializable

data class NasaPhoto(
    val date: String,
    val explanation : String,
    val media_type : String,
    val title : String,
    val url : String)
    : Serializable