package com.chow.code_base_sdk.model.domain

import com.chow.code_base_sdk.model.RelatedTopicModel
import com.chow.code_base_sdk.rest.BaseService

data class DomainCharacter (
    val name: String,
    val description: String,
    val image: String? = null
    )

fun List<RelatedTopicModel>?.mapToDomainCharacters(): List<DomainCharacter> {
    return this?.map {
        val items = it.text?.split('-') ?: emptyList()
        DomainCharacter(
            name = if(items.isNotEmpty()) items[0] else "Invalid Name",
            description = it.text ?: "No Description",
            image = it.icon?.uRL?.run {
                "${BaseService.IMAGE_BASE_URL}${this}" } ?: ""
        )
    } ?: emptyList()
}