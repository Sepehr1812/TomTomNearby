package ir.divar.domain.place.model

import com.google.gson.annotations.SerializedName

data class PlaceLocation(
    val country: String,
    val locality: String,
    val region: String,
    @SerializedName("formatted_address")
    val formattedAddress: String
)
