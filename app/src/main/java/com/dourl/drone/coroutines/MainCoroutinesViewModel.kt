package com.dourl.drone.coroutines

import androidx.lifecycle.*
import com.dourl.drone.*
import com.dourl.drone.disposables.CompositeDisposable
import com.dourl.drone.model.Poster
import com.dourl.drone.network.ErrorEnvelopeMapper
import kotlinx.coroutines.Dispatchers
import timber.log.Timber


class MainCoroutinesViewModel constructor(disneyCoroutinesService: DisneyCoroutinesService) :
    ViewModel() {

    val posterListLiveData: LiveData<List<Poster>>
    val toastLiveData = MutableLiveData<String>()
    private val disposable = CompositeDisposable()

    init {
        Timber.d("initialized MainViewModel.")
        posterListLiveData = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emitSource(
                disneyCoroutinesService.fetchDisneyPosterList().toResponseDataSource()
                    .retry(3, 5000L)
                    .dataRetainPolicy(DataRetainPolicy.RETAIN)
                    .joinDisposable(disposable)
                    .request {
                        onSuccess {
                            Timber.d("$data")
                        }.onError {
                            Timber.d(message())
                            when(statusCode){
                                StatusCode.InternalServerError -> toastLiveData.postValue("InternalServerError")
                                StatusCode.BadGateway -> toastLiveData.postValue("BadGateway")
                                else-> toastLiveData.postValue("$statusCode(${statusCode.code}): ${message()}")
                            }

                            map(ErrorEnvelopeMapper){
                                Timber.d(this.toString())
                            }
                        }.onException {
                            Timber.d(message())
                            toastLiveData.postValue(message())
                        }


                    }.asLiveData()
            )
        }
    }


    override fun onCleared() {
        super.onCleared()
        if (!disposable.disposed) {
            disposable.clear()
        }
    }
}