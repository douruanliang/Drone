package com.dourl.drone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dourl.drone.disposables.CompositeDisposable
import com.dourl.drone.model.Poster
import com.dourl.drone.network.DisneyService
import com.dourl.drone.network.ErrorEnvelopeMapper
import timber.log.Timber

class MainViewModel constructor(disneyService: DisneyService) : ViewModel() {

    val posterListLiveData:LiveData<List<Poster>>
    val toastLiveData = MutableLiveData<String>()
    val disposable = CompositeDisposable()

    init {
        posterListLiveData = disneyService.fetchDisneyPosterList().toResponseDataSource()
            .retry(3,5000L)
            .dataRetainPolicy(DataRetainPolicy.RETAIN)
            .joinDisposable(disposable)
            .request {
                onSuccess {
                    Timber.d("$data")
                }.onError {
                    Timber.d(message())
                    when(statusCode){
                        StatusCode.InternalServerError -> toastLiveData.postValue("\"InternalServerError\"")
                        StatusCode.BadGateway -> toastLiveData.postValue("BadGateway")
                        else -> toastLiveData.postValue("$statusCode(${statusCode.code}): ${message()}")
                    }

                    map(ErrorEnvelopeMapper){
                        Timber.d(this.toString())
                    }
                }.onException {
                    Timber.d(message())
                    toastLiveData.postValue(message())
                }
            }.asLiveData()
    }

    override fun onCleared() {
        super.onCleared()
        if (!disposable.disposed) {
            disposable.clear()
        }
    }






























}