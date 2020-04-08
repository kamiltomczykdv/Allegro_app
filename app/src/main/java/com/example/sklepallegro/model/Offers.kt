package com.example.sklepallegro.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Offers(val offers: List<Offer>) : Parcelable