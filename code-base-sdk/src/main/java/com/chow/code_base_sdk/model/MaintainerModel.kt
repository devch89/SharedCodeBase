package com.chow.code_base_sdk.model


import com.google.gson.annotations.SerializedName

data class MaintainerModel(
    @SerializedName("github")
    val github: String? = null
)