package com.example.aviatickets.model.network

import com.example.aviatickets.model.entity.Offer
import retrofit2.Call
import retrofit2.http.GET

interface OfferService {
    @GET("offer_list")
    fun fetchOfferList(): Call<List<Offer>>
}

// https://my-json-server.typicode.com/estharossa/fake-api-demo/offer_list