package ir.divar.domain.place

import ir.divar.domain.place.model.Place
import ir.divar.domain.place.model.PlaceGeocode
import ir.divar.domain.remote.BaseResult

interface IPlacesRepository {

    suspend fun getPlacesFromServer(placeGeocode: PlaceGeocode): BaseResult<Pair<List<Place>?, String?>, String>
    suspend fun getPlacesByLinkFromServer(url: String): BaseResult<Pair<List<Place>?, String?>, String>

    suspend fun getPlaceListFromLocal(): List<Place>?
    suspend fun insertPlaceList(placeList: List<Place>): Unit?
    suspend fun clearPlaceList(): Unit?
}