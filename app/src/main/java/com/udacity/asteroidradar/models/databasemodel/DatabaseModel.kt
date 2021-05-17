package com.udacity.asteroidradar.models.databasemodel

import androidx.room.Entity


import androidx.room.PrimaryKey
import com.udacity.asteroidradar.models.domainmodels.Asteroid
import com.udacity.asteroidradar.models.domainmodels.PictureOfDay

@Entity(tableName = "asteroid")
data class AsteroidDataBaseModel constructor (
    @PrimaryKey
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)

@Entity(tableName = "picture_of_the_day")
data class PictureOfTheDayDataBaseModel constructor (
    @PrimaryKey
    val id: Long,
    val url: String,
    val mediaType: String,
    val title: String
)

fun List<AsteroidDataBaseModel>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid(
            id = it.id,
            codename =  it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}

fun PictureOfTheDayDataBaseModel.asDomainModel(): PictureOfDay {
    return let {
        PictureOfDay(
            url = it.url,
            mediaType = it.mediaType,
            title = it.title
        )
    }
}