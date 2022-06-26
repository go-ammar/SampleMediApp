package com.example.sampleapp.data.remote.medicine

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Medicines(
    @SerializedName("problems") var problems: ArrayList<Problems> = arrayListOf()
) : Parcelable

@Parcelize
data class Problems(
    @SerializedName("disease") var disease: String? = null,
    @SerializedName("medications") var medications: ArrayList<AssociatedDrug> = arrayListOf(),
    @SerializedName("labs") var labs: ArrayList<Labs> = arrayListOf()
): Parcelable

@Parcelize
data class AssociatedDrug(
    @SerializedName("name") var name: String? = null,
    @SerializedName("dose") var dose: String? = null,
    @SerializedName("strength") var strength: String? = null
): Parcelable


@Parcelize
data class Labs(
    @SerializedName("location") var location: String? = null
): Parcelable
