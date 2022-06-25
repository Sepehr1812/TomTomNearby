package ir.divar.domain.place.usecase

import ir.divar.domain.common.GeneralUsecase
import ir.divar.domain.place.IPlacesRepository
import ir.divar.domain.place.model.Place
import javax.inject.Inject

class GetPlaceListFromLocal @Inject constructor(
    private val iPlacesRepository: IPlacesRepository
) : GeneralUsecase<GetPlaceListFromLocal.RequestValues, List<Place>>() {

    data class RequestValues(val offset: Int) : RequestValue

    override suspend fun executeUseCase(requestValues: RequestValues) =
        iPlacesRepository.getPlaceListFromLocal(requestValues.offset)
}