package ir.divar.domain.place.usecase

import ir.divar.domain.common.GeneralUsecase
import ir.divar.domain.place.IPlacesRepository
import ir.divar.domain.place.model.Place
import ir.divar.domain.place.model.PlacePosition
import ir.divar.domain.place.model.PlaceSummaryResponse
import ir.divar.domain.remote.BaseResult
import javax.inject.Inject

class GetPlaceListFromServer @Inject constructor(
    private val iPlacesRepository: IPlacesRepository
) : GeneralUsecase<GetPlaceListFromServer.RequestValues, BaseResult<Pair<PlaceSummaryResponse, List<Place>?>, String>>() {

    data class RequestValues(val placePosition: PlacePosition) : RequestValue

    override suspend fun executeUseCase(requestValues: RequestValues) =
        iPlacesRepository.getPlacesFromServer(requestValues.placePosition)
}