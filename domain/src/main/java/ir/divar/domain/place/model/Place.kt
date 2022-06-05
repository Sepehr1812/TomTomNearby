package ir.divar.domain.place.model

data class Place(
    val id: String,
    val name: String,
    val categories: String,
    val distance: Int,
    val geocodes: PlaceGeocodes,
    val location: PlaceLocation
)