package com.udacity.asteroidradar.main

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_ETHERNET
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkCapabilities.*
import android.os.Build
import android.provider.ContactsContract.CommonDataKinds.Email.TYPE_MOBILE
import androidx.lifecycle.*
import com.udacity.asteroidradar.AsteroidApplicationClass
import com.udacity.asteroidradar.repository.Repository
import com.udacity.asteroidradar.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(val app: Application) : AndroidViewModel(app){

    private val asteroidRepository = Repository()
    val asteroidList = asteroidRepository.asteroidList
    val pictureOfDay = asteroidRepository.pictureOfTheDayData

    val loadingLiveData :MutableLiveData<Resource<Boolean>> = MutableLiveData()

    init {

        loadingLiveData.postValue(Resource.Loading())
        if (true) {

            viewModelScope.launch(Dispatchers.IO) {try {
                asteroidRepository.refreshAsteroid()
                asteroidRepository.refreshPictureOfDay()
                loadingLiveData.postValue(Resource.Success(true))
            }catch (e: Exception){
                loadingLiveData.postValue(Resource.Error(e.message))
            }


            }
        }else{
            loadingLiveData.postValue(Resource.Error("No Internet"))
        }

    }

  /*  private fun getDataFromDatabase() {


    }*/
    private fun hasInternetConnection() : Boolean
    {

        val  connectivityManager = getApplication<AsteroidApplicationClass>().getSystemService(
                Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            val network = connectivityManager.activeNetwork ?: return  false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return  false
            return  when{
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }
        else
        {
            connectivityManager.activeNetworkInfo?.run {
                return  when(type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }


}

class MainViewModelFactory(val app: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return  MainViewModel(app ) as T
    }
}