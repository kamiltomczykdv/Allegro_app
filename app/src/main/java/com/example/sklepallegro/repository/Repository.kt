package com.example.sklepallegro.repository

import android.content.Context
import com.example.sklepallegro.model.Offers
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val base_url = "https://private-987cdf-allegromobileinterntest.apiary-mock.com/"

class Repository() {
    private lateinit var retrofit: Retrofit
    private lateinit var offerService: OfferService
    private lateinit var networkManager: NetworkManager

    fun getInstance() {
        retrofit = Retrofit.Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(base_url)
            .build()
        offerService = retrofit.create(OfferService::class.java)
        networkManager = NetworkManager()
    }

    fun getOffers(): Observable<Offers> = offerService
        .getOffers()
        .subscribeOn(Schedulers.io())

    fun checkConnection(context: Context) = networkManager.getReactiveNetwork(context)
}
