package ir.divar.data.place

import ir.divar.data.place.local.PlaceLocalRepository
import ir.divar.data.place.remote.PlaceRemoteRepository
import ir.divar.domain.place.IPlacesRepository
import ir.divar.domain.place.model.LatLng
import ir.divar.domain.place.model.Place

class PlaceRepository(
    private val placeRemoteRepository: PlaceRemoteRepository,
    private val placeLocalRepository: PlaceLocalRepository
) : IPlacesRepository {

    override suspend fun getPlaces(latLng: LatLng) =
        placeRemoteRepository.getPlaces(latLng)

    override suspend fun getPlacesByLink(url: String) =
        placeRemoteRepository.getPlacesByLink(url)

    override suspend fun getPlaceListFromLocal() =
        placeLocalRepository.getPlaceListFromLocal()

    override suspend fun insertPlaceList(placeList: List<Place>) =
        placeLocalRepository.insertPlaceList(placeList)
}