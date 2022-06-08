package ir.divar.interviewtask.util

import com.google.android.gms.maps.model.LatLng
import ir.divar.domain.place.model.LatLng as PlaceModelLatLng

fun LatLng.toLocalString() = "${latitude},${longitude}"

fun String.toLatLang() = split(",").let { LatLng(it[0].toDouble(), it[1].toDouble()) }

fun PlaceModelLatLng.toGmsLatLng() = LatLng(latitude, longitude)