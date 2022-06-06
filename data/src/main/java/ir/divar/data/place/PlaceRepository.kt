package ir.divar.data.place

import ir.divar.data.place.remote.PlaceRemoteRepository
import ir.divar.domain.place.IPlacesRepository
import ir.divar.domain.place.model.LatLng

class PlaceRepository(
    private val placeRemoteRepository: PlaceRemoteRepository
) : IPlacesRepository {

    override suspend fun getPlaces(latLng: LatLng) =
        placeRemoteRepository.getPlaces(latLng)
}