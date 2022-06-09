package ir.divar.domain.place.usecase

import ir.divar.domain.common.GeneralUsecase
import ir.divar.domain.place.IPlacesRepository
import ir.divar.domain.place.model.Place
import ir.divar.domain.place.model.PlaceGeocode
import ir.divar.domain.remote.BaseResult
import javax.inject.Inject

class GetPlaceListFromServer @Inject constructor(
    private val iPlacesRepository: IPlacesRepository
) : GeneralUsecase<GetPlaceListFromServer.RequestValues, BaseResult<Pair<List<Place>?, String?>, String>>() {

    data class RequestValues(val placeGeocode: PlaceGeocode) : RequestValue

    override suspend fun executeUseCase(requestValues: RequestValues) =
        iPlacesRepository.getPlacesFromServer(requestValues.placeGeocode)
}