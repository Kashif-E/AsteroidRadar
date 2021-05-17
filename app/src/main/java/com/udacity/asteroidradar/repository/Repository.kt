package com.udacity.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.Constants.database
import com.udacity.asteroidradar.api.Retrofit
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.models.databasemodel.asDomainModel
import com.udacity.asteroidradar.models.domainmodels.Asteroid
import com.udacity.asteroidradar.models.domainmodels.PictureOfDay
import com.udacity.asteroidradar.models.networkmodel.asDatabaseModel
import com.udacity.asteroidradar.utils.getFormatedEndDate
import com.udacity.asteroidradar.utils.getFormatedStartDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException

class Repository {


    val asteroidList: LiveData<List<Asteroid>> = Transformations.map(database.asteroid.getAllAsteroid()) {
        it?.asDomainModel()
    }

    val pictureOfTheDayData: LiveData<PictureOfDay> = Transformations.map(database.asteroid.getPictureOfTheDay()) {
        it?.let {
            it.asDomainModel()
        }
    }

    suspend fun refreshAsteroid() {
        withContext(Dispatchers.IO) {
            try {


                val asteroidList = Retrofit.api.getAsteroidListAsync(getFormatedStartDate(), getFormatedEndDate(), API_KEY).await()

                Log.e("resposne", asteroidList)

               val asteroidParsed = parseAsteroidsJsonResult(JSONObject(asteroidList.toString()))

               database.asteroid.upsettAllAsteroids(asteroidParsed)
            } catch (e: HttpException) {
                Log.e("refreshAsteroid", e.localizedMessage)
            }
        }
    }

    suspend fun refreshPictureOfDay() {
        withContext(Dispatchers.IO) {
            try {
                val pictureOfTheDay = Retrofit.api.getPictureOfTheDayAsync(API_KEY).await()
                database.asteroid.upsertPictureOfTheDay(pictureOfTheDay.asDatabaseModel())
            } catch (e: HttpException) {
                Log.e("refreshPictureOfDay", e.localizedMessage)
            }
        }
    }

    suspend fun deleteAllTable() {
        withContext(Dispatchers.IO) {
            database.asteroid.deleteAllAsteroids()
            database.asteroid.deleteAllPictures()
        }
    }
}