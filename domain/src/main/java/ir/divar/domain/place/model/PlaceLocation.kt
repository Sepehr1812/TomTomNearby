package ir.divar.domain.place.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class PlaceLocation(
    val country: String = "",
    val locality: String = "",
    val region: String = "",
    @SerializedName("formatted_address")
    val formattedAddress: String = ""
) : Parcelable
