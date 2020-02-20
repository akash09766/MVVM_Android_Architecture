package com.skylightdevelopers.android.udemystateofartandroidapp.model


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Taxonomy(
    @SerializedName("family")
    val family: String?,
    @SerializedName("kingdom")
    val kingdom: String?,
    @SerializedName("order")
    val order: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(family)
        parcel.writeString(kingdom)
        parcel.writeString(order)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Taxonomy> {
        override fun createFromParcel(parcel: Parcel): Taxonomy {
            return Taxonomy(parcel)
        }

        override fun newArray(size: Int): Array<Taxonomy?> {
            return arrayOfNulls(size)
        }
    }
}