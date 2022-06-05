package ir.divar.domain.place.model

data class PlaceGeocodes(val main: LatLng)

data class LatLng(val latitude: Double, val longitude: Double)