package com.udacity.asteroidradar.models.networkmodel

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.models.domainmodels.PictureOfDay
import com.udacity.asteroidradar.models.databasemodel.PictureOfTheDayDataBaseModel

@JsonClass(generateAdapter = true)
data class PictureOfTheDayNetworkResponse(
    val title: String,

    @Json(name = "media_type")
    val mediaType: String,

    val url: String
)

fun PictureOfTheDayNetworkResponse.asDomainModel(): PictureOfDay {
    return let {
        PictureOfDay(
            url = it.url,
            mediaType = it.mediaType,
            title = it.title
        )
    }
}

fun PictureOfTheDayNetworkResponse.asDatabaseModel(): PictureOfTheDayDataBaseModel {
    return let {
        PictureOfTheDayDataBaseModel(
            id = 1,
            url = it.url,
            mediaType = it.mediaType,
            title = it.title
        )
    }
}