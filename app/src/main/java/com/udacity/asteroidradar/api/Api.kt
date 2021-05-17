package com.udacity.asteroidradar.api


import com.udacity.asteroidradar.Constants.FEED_ENDPOINT
import com.udacity.asteroidradar.Constants.PICTURE_OF_THE_DAY
import com.udacity.asteroidradar.models.networkmodel.PictureOfTheDayNetworkResponse
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {


    @GET(FEED_ENDPOINT)
    fun getAsteroidListAsync(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String
    ): Deferred<String>





    @GET(PICTURE_OF_THE_DAY)
    fun getPictureOfTheDayAsync(
        @Query("api_key") apiKey: String
    ): Deferred<PictureOfTheDayNetworkResponse>
}
