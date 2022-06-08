package ir.divar.domain.place

import ir.divar.domain.place.model.LatLng
import ir.divar.domain.place.model.Place
import ir.divar.domain.remote.BaseResult

interface IPlacesRepository {

    suspend fun getPlacesFromServer(latLng: LatLng): BaseResult<List<Place>, String>
    suspend fun getPlacesByLinkFromServer(url: String): BaseResult<List<Place>, String>

    suspend fun getPlaceListFromLocal(): List<Place>?
    suspend fun insertPlaceList(placeList: List<Place>): Unit?
    suspend fun clearPlaceList(): Unit?
}