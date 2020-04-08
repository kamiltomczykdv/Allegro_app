package com.example.sklepallegro.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Offer(
    val id: String,
    val name: String,
    val thumbnailUrl: String,
    val price: Price,
    val description: String
) : Parcelable