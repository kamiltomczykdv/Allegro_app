package com.example.sklepallegro.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.example.sklepallegro.model.Offers
import com.example.sklepallegro.repository.Repository
import io.reactivex.Observable

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: Repository

    init {
        repository = Repository()
        repository.getInstance()
    }

    fun getOffers(): Observable<Offers> = repository.getOffers()
    fun checkConnection(context: Context) = repository.checkConnection(context)
}