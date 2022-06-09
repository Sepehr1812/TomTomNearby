package ir.divar.domain.place.usecase

import ir.divar.domain.common.GeneralUsecase
import ir.divar.domain.place.IPlacesRepository
import ir.divar.domain.place.model.Place
import javax.inject.Inject

class InsertPlaceList @Inject constructor(
    private val iPlacesRepository: IPlacesRepository
) : GeneralUsecase<InsertPlaceList.RequestValues, Unit>() {

    data class RequestValues(val placeList: List<Place>) : RequestValue

    override suspend fun executeUseCase(requestValues: RequestValues) =
        iPlacesRepository.insertPlaceList(requestValues.placeList)
}