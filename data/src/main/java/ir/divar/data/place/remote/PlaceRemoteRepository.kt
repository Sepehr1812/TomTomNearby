package ir.divar.data.place.remote

import ir.divar.data.remote.safeApiCall
import ir.divar.domain.place.model.LatLng
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlaceRemoteRepository(
    private val iPlaceApi: IPlaceApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getPlaces(latLng: LatLng) = withContext(dispatcher) {
        iPlaceApi.safeApiCall {
            getPlaces("${latLng.latitude},${latLng.longitude}").determineStatus()
        }
    }
}