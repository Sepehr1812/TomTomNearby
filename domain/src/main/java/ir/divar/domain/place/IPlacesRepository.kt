package ir.divar.domain.place

import ir.divar.domain.place.model.Place
import ir.divar.domain.place.model.PlacePosition
import ir.divar.domain.place.model.PlaceSummaryResponse
import ir.divar.domain.remote.BaseResult

interface IPlacesRepository {

    suspend fun getPlacesFromServer(
        placePosition: PlacePosition,
        offset: Int
    ): BaseResult<Pair<PlaceSummaryResponse, List<Place>?>, String>

    suspend fun getPlaceListFromLocal(): List<Place>?
    suspend fun insertPlaceList(placeList: List<Place>): Unit?
    suspend fun clearPlaceList(): Unit?
}