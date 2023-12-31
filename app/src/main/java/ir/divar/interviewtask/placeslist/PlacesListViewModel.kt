package ir.divar.interviewtask.placeslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.divar.domain.place.model.Place
import ir.divar.domain.place.usecase.ClearPlaceList
import ir.divar.domain.place.usecase.GetPlaceListFromLocal
import ir.divar.domain.place.usecase.GetPlaceListFromServer
import ir.divar.domain.place.usecase.InsertPlaceList
import ir.divar.domain.remote.BaseResult
import ir.divar.interviewtask.util.Constants.PROBLEM_OCCURRED_ERROR_MESSAGE
import ir.divar.interviewtask.util.SingleLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject
import ir.divar.domain.place.model.PlacePosition as PlaceModelLatLng

@HiltViewModel
class PlacesListViewModel @Inject constructor(
    private val getPlaceListFromLocal: GetPlaceListFromLocal,
    private val getPlaceListFromServer: GetPlaceListFromServer,
    private val insertPlaceList: InsertPlaceList,
    private val clearPlaceList: ClearPlaceList
) : ViewModel() {

    val getLocalPlaceListResponse = SingleLiveEvent<List<Place>>()
    val getLocalPlaceListError = SingleLiveEvent<Unit>()

    val getLocalPlaceListByOffsetResponse = SingleLiveEvent<List<Place>>()
    val getLocalPlaceListByOffsetError = SingleLiveEvent<Unit>()

    val getServerPlaceListNextOffsetResponse = SingleLiveEvent<Int?>()

    val getServerPlaceListResponse = SingleLiveEvent<List<Place>>()
    val getServerPlaceListError = SingleLiveEvent<String>()

    val getServerPlaceListByOffsetResponse = SingleLiveEvent<List<Place>>()
    val getServerPlaceListByOffsetError = SingleLiveEvent<String>()

    val insertPlaceListResponse = SingleLiveEvent<Unit>()
    val insertPlaceListError = SingleLiveEvent<Unit>()

    val clearPlaceListResponse = SingleLiveEvent<Unit>()
    val clearPlaceListError = SingleLiveEvent<Unit>()

    /** handles exceptions may be occurred in coroutine scopes */
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, e ->
        e.printStackTrace()
    }

    fun getLocalPlaceList(offset: Int = 0) {
        viewModelScope.launch(coroutineExceptionHandler) {
            getPlaceListFromLocal.executeUseCase(GetPlaceListFromLocal.RequestValues(offset))
                ?.also {
                    if (offset == 0)
                        getLocalPlaceListResponse.value = it
                    else getLocalPlaceListByOffsetResponse.value = it
                } ?: apply {
                if (offset == 0) getLocalPlaceListError.value = Unit
                else getLocalPlaceListByOffsetError.value = Unit
            }
        }
    }

    fun getServerPlaceList(latLng: LatLng, offset: Int = 0) {
        viewModelScope.launch(coroutineExceptionHandler) {
            getPlaceListFromServer.executeUseCase(
                GetPlaceListFromServer.RequestValues(
                    PlaceModelLatLng(latLng.latitude, latLng.longitude),
                    offset
                )
            ).also {
                when (it) {
                    is BaseResult.Success -> it.data?.second?.also { placeList ->
                        if (offset == 0)
                            getServerPlaceListResponse.value = placeList
                        else getServerPlaceListByOffsetResponse.value = placeList

                        getServerPlaceListNextOffsetResponse.value = it.data?.first?.run {
                            this.offset.plus(numResults).let { nextOffset ->
                                if (nextOffset == totalResults) null else nextOffset
                            }
                        }
                    } ?: apply { getServerPlaceListError.value = PROBLEM_OCCURRED_ERROR_MESSAGE }

                    is BaseResult.Error ->
                        (it.message ?: PROBLEM_OCCURRED_ERROR_MESSAGE).also { message ->
                            if (offset == 0) getServerPlaceListError.value = message
                            else getServerPlaceListByOffsetError.value = message
                        }
                }
            }
        }
    }

    fun insertPlaceList(placeList: List<Place>) {
        viewModelScope.launch(coroutineExceptionHandler) {
            insertPlaceList.executeUseCase(InsertPlaceList.RequestValues(placeList))?.also {
                insertPlaceListResponse.value = it
            } ?: apply { insertPlaceListError.value = Unit }
        }
    }

    fun clearPlaceList() {
        viewModelScope.launch(coroutineExceptionHandler) {
            clearPlaceList.executeUseCase(ClearPlaceList.RequestValues())?.also {
                clearPlaceListResponse.value = Unit
            } ?: apply { clearPlaceListError.value = Unit }
        }
    }
}