package com.dourl.drone.coroutines

import com.dourl.drone.DataSource
import com.dourl.drone.model.Poster
import retrofit2.http.GET


interface DisneyCoroutinesService {

    @GET("DisneyPosters.json")
    suspend fun fetchDisneyPosterList(): DataSource<List<Poster>>
}