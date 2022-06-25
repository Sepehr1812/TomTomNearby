package ir.divar.data.place

import ir.divar.data.place.local.PlaceLocalRepository
import ir.divar.data.place.remote.PlaceRemoteRepository
import ir.divar.domain.place.IPlacesRepository
import ir.divar.domain.place.model.Place
import ir.divar.domain.place.model.PlacePosition
import javax.inject.Inject

class PlaceRepository @Inject constructor(
    private val placeRemoteRepository: PlaceRemoteRepository,
    private val placeLocalRepository: PlaceLocalRepository
) : IPlacesRepository {

    override suspend fun getPlacesFromServer(placePosition: PlacePosition, offset: Int) =
        placeRemoteRepository.getPlaces(placePosition, offset)

    override suspend fun getPlaceListFromLocal() =
        placeLocalRepository.getPlaceListFromLocal()

    override suspend fun insertPlaceList(placeList: List<Place>) =
        placeLocalRepository.insertPlaceList(placeList)

    override suspend fun clearPlaceList() =
        placeLocalRepository.clearPlaceList()
}