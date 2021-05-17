package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.models.databasemodel.AsteroidDataBaseModel
import com.udacity.asteroidradar.models.databasemodel.PictureOfTheDayDataBaseModel
import com.udacity.asteroidradar.models.domainmodels.Asteroid

@Dao
interface AsteroidDao {
    @Query("SELECT * from asteroid ORDER by closeApproachDate ASC")
    fun getAllAsteroid(): LiveData<List<AsteroidDataBaseModel>>

    @Query("SELECT * from picture_of_the_day WHERE id=1")
    fun getPictureOfTheDay(): LiveData<PictureOfTheDayDataBaseModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsettAllAsteroids(asteroidDataBaseModels: ArrayList<AsteroidDataBaseModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertPictureOfTheDay(picture: PictureOfTheDayDataBaseModel)

    @Query("DELETE FROM asteroid")
    fun deleteAllAsteroids()

    @Query("DELETE FROM picture_of_the_day")
    fun deleteAllPictures()
}

