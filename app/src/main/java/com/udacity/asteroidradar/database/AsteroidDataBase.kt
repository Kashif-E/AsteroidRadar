package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.models.databasemodel.AsteroidDataBaseModel
import com.udacity.asteroidradar.models.databasemodel.PictureOfTheDayDataBaseModel

@Database(entities = [AsteroidDataBaseModel::class, PictureOfTheDayDataBaseModel::class], version = 1)
abstract class AsteroidDatabase: RoomDatabase() {
    abstract val asteroid: AsteroidDao
}

private lateinit var INSTANCE: AsteroidDatabase

fun getDatabaseInstance(context: Context): AsteroidDatabase {
    synchronized(AsteroidDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext, AsteroidDatabase::class.java, "asteroid")
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    return INSTANCE
}
