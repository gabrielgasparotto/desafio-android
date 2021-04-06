package com.picpay.desafio.android.users.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

interface User : Parcelable {
    val id: Int
    val img: String
    val name: String
    val username: String
}