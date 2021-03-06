package com.test.nasapicturestestapp.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NasaPicture(
    @SerializedName("copyright") val copyright: String? = null,
    @SerializedName("date") val date: String,
    @SerializedName("explanation") val explanation: String,
    @SerializedName("hdurl") val hdUrl: String,
    @SerializedName("media_type") val mediaType: String,
    @SerializedName("service_version") val serviceVersion: String,
    @SerializedName("title") val title: String,
    @SerializedName("url") val url: String,
): Serializable
