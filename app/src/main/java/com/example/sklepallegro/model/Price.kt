package com.example.sklepallegro.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Price(
    val amount: Double,
    val currency: String
) : Parcelable