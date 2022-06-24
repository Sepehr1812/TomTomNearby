package ir.divar.data.place.remote

import ir.divar.data.place.remote.model.PlaceRemoteMapper
import ir.divar.data.remote.safeApiCall
import ir.divar.domain.place.model.PlacePosition
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlaceRemoteRepository @Inject constructor(
    private val iPlaceApi: IPlaceApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getPlaces(placePosition: PlacePosition) = withContext(dispatcher) {
        iPlaceApi.safeApiCall {
            getPlaces(placePosition.latitude.toFloat(), placePosition.longitude.toFloat(), 0).let {
                // extract data as a list of places
                val results =
                    it.results?.map { place -> PlaceRemoteMapper.mapToDomain(place) }

                // extract summary of places results
                val summary = it.summary?.let { summaryRemoteModel ->
                    PlaceRemoteMapper.mapSummaryToDomain(summaryRemoteModel)
                }

                it.determineStatus(
                    if (results == null || summary == null) null // pass null to get Error
                    else Pair(summary, results) // pass valid result
                )
            }
        }
    }
}