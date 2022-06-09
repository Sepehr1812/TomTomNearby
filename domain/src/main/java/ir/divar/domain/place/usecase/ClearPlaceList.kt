package ir.divar.domain.place.usecase

import ir.divar.domain.common.GeneralUsecase
import ir.divar.domain.place.IPlacesRepository
import javax.inject.Inject

class ClearPlaceList @Inject constructor(
    private val iPlacesRepository: IPlacesRepository
) : GeneralUsecase<ClearPlaceList.RequestValues, Unit>() {

    class RequestValues : RequestValue

    override suspend fun executeUseCase(requestValues: RequestValues) =
        iPlacesRepository.clearPlaceList()
}