package ir.divar.domain.place.usecase

import ir.divar.domain.common.GeneralUsecase
import ir.divar.domain.place.IPlacesRepository
import ir.divar.domain.place.model.Place
import ir.divar.domain.remote.BaseResult
import javax.inject.Inject

class GetPlaceListByLinkFromServer @Inject constructor(
    private val iPlacesRepository: IPlacesRepository
) : GeneralUsecase<GetPlaceListByLinkFromServer.RequestValues, BaseResult<Pair<List<Place>?, String?>, String>>() {

    data class RequestValues(val url: String) : RequestValue

    override suspend fun executeUseCase(requestValues: RequestValues) =
        iPlacesRepository.getPlacesByLinkFromServer(requestValues.url)
}