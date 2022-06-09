package ir.divar.interviewtask.placeslist

import androidx.lifecycle.MutableLiveData
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
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject
import ir.divar.domain.place.model.PlaceGeocode as PlaceModelLatLng

@HiltViewModel
class PlacesListViewModel @Inject constructor(
    private val getPlaceListFromLocal: GetPlaceListFromLocal,
    private val getPlaceListFromServer: GetPlaceListFromServer,
    private val insertPlaceList: InsertPlaceList,
    private val clearPlaceList: ClearPlaceList
) : ViewModel() {

    val getLocalPlaceListResponse = MutableLiveData<List<Place>>()
    val getLocalPlaceListError = MutableLiveData<Unit>()

    val getServerPlaceListResponse = MutableLiveData<List<Place>>()
    val getServerPlaceListError = MutableLiveData<String>()

    val insertPlaceListResponse = MutableLiveData<Unit>()
    val insertPlaceListError = MutableLiveData<Unit>()

    val clearPlaceListResponse = MutableLiveData<Unit>()
    val clearPlaceListError = MutableLiveData<Unit>()

    /** handles exceptions may be occurred in coroutine scopes */
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, e ->
        e.printStackTrace()
    }

    fun getLocalPlaceList() {
        viewModelScope.launch(coroutineExceptionHandler) {
            getPlaceListFromLocal.executeUseCase(GetPlaceListFromLocal.RequestValues())?.also {
                getLocalPlaceListResponse.value = it
            } ?: apply { getLocalPlaceListError.value = Unit }
        }
    }

    fun getServerPlaceList(latLng: LatLng) {
        viewModelScope.launch(coroutineExceptionHandler) {
            getPlaceListFromServer.executeUseCase(
                GetPlaceListFromServer.RequestValues(
                    PlaceModelLatLng(latLng.latitude, latLng.longitude)
                )
            ).also {
                when (it) {
                    is BaseResult.Success -> it.data?.also { placeList ->
                        getServerPlaceListResponse.value = placeList
                    } ?: apply { getServerPlaceListError.value = PROBLEM_OCCURRED_ERROR_MESSAGE }

                    is BaseResult.Error -> getServerPlaceListError.value =
                        it.message ?: PROBLEM_OCCURRED_ERROR_MESSAGE
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