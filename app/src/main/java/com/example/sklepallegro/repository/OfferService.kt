package com.example.sklepallegro.repository

import com.example.sklepallegro.model.Offers
import io.reactivex.Observable
import retrofit2.http.GET

interface OfferService {
    @GET("allegro/offers")
    fun getOffers(): Observable<Offers>
}