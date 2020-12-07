package com.dourl.drone.network

import com.dourl.drone.DataSource
import com.dourl.drone.model.Poster
import retrofit2.http.GET

interface DisneyService {
    @GET("DisneyPoster.json")
    fun fetchDisneyPosterList():DataSource<List<Poster>>
}