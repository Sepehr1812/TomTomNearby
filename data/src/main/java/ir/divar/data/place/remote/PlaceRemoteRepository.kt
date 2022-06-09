package ir.divar.data.place.remote

import ir.divar.data.place.remote.model.PlaceRemoteMapper
import ir.divar.data.remote.safeApiCall
import ir.divar.data.util.Constants.LINK_HEADER_KEY
import ir.divar.domain.place.model.PlaceGeocode
import ir.divar.domain.remote.BaseResult
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
                // extract data as a list of places
                val results =
                    it.body()?.results?.map { place -> PlaceRemoteMapper.mapToDomain(place) }

                // extract next page link from header to implement pagination
                val nextLink =
                    it.headers().get(LINK_HEADER_KEY)?.substringAfter('<')?.substringBefore('>')

                it.body()?.determineStatus(Pair(results, nextLink))
            } ?: BaseResult.Success(null)
        }
    }

    suspend fun getPlacesByLink(url: String) = withContext(dispatcher) {
        iPlaceApi.safeApiCall {
            getPlacesByLink(url).let {
                // extract data as a list of places
                val results =
                    it.body()?.results?.map { place -> PlaceRemoteMapper.mapToDomain(place) }

                // extract next page link from header to implement pagination
                val nextLink =
                    it.headers().get(LINK_HEADER_KEY)?.substringAfter('<')?.substringBefore('>')

                it.body()?.determineStatus(Pair(results, nextLink))
            } ?: BaseResult.Success(null)
        }
    }
}