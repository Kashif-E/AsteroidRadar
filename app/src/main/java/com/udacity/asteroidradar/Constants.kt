package com.udacity.asteroidradar

import com.udacity.asteroidradar.database.AsteroidDatabase

object Constants {
    const val API_QUERY_DATE_FORMAT = "YYYY-MM-dd"
    const val DEFAULT_END_DATE_DAYS = 7
    const val BASE_URL = "https://api.nasa.gov/"
    const val FEED_ENDPOINT= "/neo/rest/v1/feed"
    const val PICTURE_OF_THE_DAY ="/planetary/apod"
    const val API_KEY ="o"
    lateinit var database :AsteroidDatabase

}