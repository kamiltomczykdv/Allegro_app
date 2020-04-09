package com.example.sklepallegro.ui

import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.sklepallegro.R
import com.example.sklepallegro.model.Offer
import com.example.sklepallegro.viewModel.MainViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var itemsAdapter: ItemsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var temporaryOffersList: MutableList<Offer>
    private var offersDisposable: Disposable? = null
    private var connectionDisposable: Disposable? = null

    companion object {
        const val argumentName: String = "OFFER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()
        checkConnection()
    }

    override fun onDestroy() {
        super.onDestroy()
        connectionDisposable?.dispose()
        offersDisposable?.dispose()
    }

    private fun checkConnection() {
        connectionDisposable = mainViewModel.checkConnection(this)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { connectivity ->
                run {
                    if (connectivity.state() == NetworkInfo.State.CONNECTED) {
                        progressBar.visibility = View.VISIBLE
                        getOffers()
                    } else {
                        Toast.makeText(this, "There is no internet connection", Toast.LENGTH_LONG)
                            .show()
                        progressBar.visibility = View.INVISIBLE
                    }
                }
            }
    }

    private fun getOffers() {
        offersDisposable = mainViewModel.getOffers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
            }
            .subscribe { offers ->
                run {
                    priceRange(offers.offers)
                    sort()
                    initList(temporaryOffersList)
                }
            }
    }

    private fun priceRange(offersList: List<Offer>){
        for (offer:Offer in offersList){
            if (offer.price.amount in 50.0..1000.0){
                temporaryOffersList.add(offer)
            }
        }
    }

    private fun sort(){
        for (i in temporaryOffersList.indices){
            for (j in 0 until temporaryOffersList.size-1){
                if (temporaryOffersList[j].price.amount>temporaryOffersList[j+1].price.amount){
                    val temp=temporaryOffersList[j]
                    temporaryOffersList[j]=temporaryOffersList[j+1]
                    temporaryOffersList[j+1]=temp
                }
            }
        }
    }

    private fun initUi() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        recyclerView = findViewById(R.id.rv_items)
        progressBar = findViewById(R.id.main_pb)
        temporaryOffersList= mutableListOf()
    }

    private fun initList(offerList: List<Offer>) {
        progressBar.visibility = View.INVISIBLE
        itemsAdapter = ItemsAdapter(this, offerList) { offer -> itemClicked(offer) }
        recyclerView.adapter = itemsAdapter
        itemsAdapter.notifyDataSetChanged()
    }

    private fun itemClicked(offer: Offer) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.main_layout, getDetailsFragment(offer))
            .commit()
    }

    private fun getDetailsFragment(offer: Offer): DetailsFragment {
        val detailsFragment = DetailsFragment()
        val argument = Bundle()
        argument.putParcelable(argumentName, offer)
        detailsFragment.arguments = argument
        return detailsFragment
    }
}
