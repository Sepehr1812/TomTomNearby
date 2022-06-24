package ir.divar.data.place.remote.model

import com.google.gson.annotations.SerializedName
import ir.divar.domain.place.model.PlaceAddress
import ir.divar.domain.place.model.PlacePosition

data class PlaceRemoteModel(
    val id: String,
    @SerializedName("dist")
    val distance: Double = -1.0,
    @SerializedName("poi")
    val placeInfo: PlaceInfoRemoteModel,
    val address: PlaceAddress,
    val position: PlacePosition
) {

    data class PlaceInfoRemoteModel(
        val name: String,
        val categories: List<String>
    )
}