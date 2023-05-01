package com.chow.code_base_sdk.model


import com.google.gson.annotations.SerializedName

data class RelatedTopicModel(
    @SerializedName("FirstURL")
    val firstURL: String? = null,
    @SerializedName("Icon")
    val icon: IconModel? = null,
    @SerializedName("Result")
    val result: String? = null,
    @SerializedName("Text")
    val text: String? = null
)