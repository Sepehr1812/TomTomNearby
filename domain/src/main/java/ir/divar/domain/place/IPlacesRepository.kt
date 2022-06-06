package ir.divar.domain.place

import ir.divar.domain.place.model.LatLng
import ir.divar.domain.place.model.Place
import ir.divar.domain.remote.BaseResult

interface IPlacesRepository {

    suspend fun getPlaces(latLng: LatLng): BaseResult<List<Place>, String>
}