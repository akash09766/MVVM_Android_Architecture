package com.skylightdevelopers.android.udemystateofartandroidapp.model


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class AnimalData(
    @SerializedName("diet")
    val diet: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("lifespan")
    val lifespan: String?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("speed")
    val speed: Speed?,
    @SerializedName("taxonomy")
    val taxonomy: Taxonomy?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Speed::class.java.classLoader),
        parcel.readParcelable(Taxonomy::class.java.classLoader)
    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(diet)
        parcel.writeString(image)
        parcel.writeString(lifespan)
        parcel.writeString(location)
        parcel.writeString(name)
        parcel.writeParcelable(speed, flags)
        parcel.writeParcelable(taxonomy, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AnimalData> {
        override fun createFromParcel(parcel: Parcel): AnimalData {
            return AnimalData(parcel)
        }

        override fun newArray(size: Int): Array<AnimalData?> {
            return arrayOfNulls(size)
        }
    }
}