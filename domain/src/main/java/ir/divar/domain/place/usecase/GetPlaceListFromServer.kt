package ir.divar.domain.place.usecase

import ir.divar.domain.common.GeneralUsecase
import ir.divar.domain.place.IPlacesRepository
import ir.divar.domain.place.model.LatLng
import ir.divar.domain.place.model.Place
import ir.divar.domain.remote.BaseResult
import javax.inject.Inject

class GetPlaceListFromServer @Inject constructor(
    private val iPlacesRepository: IPlacesRepository
) : GeneralUsecase<GetPlaceListFromServer.RequestValues, BaseResult<List<Place>, String>>() {

    data class RequestValues(val latLng: LatLng) : RequestValue

    override suspend fun executeUseCase(requestValues: RequestValues) =
        iPlacesRepository.getPlacesFromServer(requestValues.latLng)
}