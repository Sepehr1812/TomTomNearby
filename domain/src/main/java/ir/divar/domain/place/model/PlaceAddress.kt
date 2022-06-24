package ir.divar.domain.place.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class PlaceAddress(
    val countryCode: String = "",
    @SerializedName("countrySubdivision")
    val region: String = "",
    val freeformAddress: String = ""
) : Parcelable
