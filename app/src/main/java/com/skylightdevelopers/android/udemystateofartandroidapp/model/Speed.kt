package com.skylightdevelopers.android.udemystateofartandroidapp.model


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Speed(
    @SerializedName("imperial")
    val imperial: String?,
    @SerializedName("metric")
    val metric: String?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(imperial)
        parcel.writeString(metric)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Speed> {
        override fun createFromParcel(parcel: Parcel): Speed {
            return Speed(parcel)
        }

        override fun newArray(size: Int): Array<Speed?> {
            return arrayOfNulls(size)
        }
    }
}