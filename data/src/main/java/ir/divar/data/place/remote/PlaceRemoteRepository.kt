package ir.divar.data.place.remote

import ir.divar.data.place.remote.model.PlaceRemoteMapper
import ir.divar.data.remote.safeApiCall
import ir.divar.domain.place.model.PlaceGeocode
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlaceRemoteRepository @Inject constructor(
    private val iPlaceApi: IPlaceApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getPlaces(placeGeocode: PlaceGeocode) = withContext(dispatcher) {
        iPlaceApi.safeApiCall {
            getPlaces("${placeGeocode.latitude},${placeGeocode.longitude}").let {
                it.determineStatus(it.result?.map { place -> PlaceRemoteMapper.mapToDomain(place) })
            }
        }
    }

    suspend fun getPlacesByLink(url: String) = withContext(dispatcher) {
        iPlaceApi.safeApiCall {
            getPlacesByLink(url).let {
                it.determineStatus(it.result?.map { place -> PlaceRemoteMapper.mapToDomain(place) })
            }
        }
    }
}