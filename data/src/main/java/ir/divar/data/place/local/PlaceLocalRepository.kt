package ir.divar.data.place.local

import android.util.Log
import ir.divar.data.db.executeQuery
import ir.divar.data.place.local.model.PlaceLocalMapper
import ir.divar.data.util.Constants
import ir.divar.domain.place.model.Place
import javax.inject.Inject

class PlaceLocalRepository @Inject constructor(
    private val iPlaceDao: IPlaceDao
) {

    suspend fun getPlaceListFromLocal(offset: Int) = iPlaceDao.executeQuery({
        getPlaceListFromLocal(offset).map { PlaceLocalMapper.mapToDomain(it) }
    }, onError = { Log.e(Constants.PLACE_TAG, "error getting places") })

    suspend fun insertPlaceList(placeList: List<Place>) = iPlaceDao.executeQuery({
        insertPlaceList(placeList.map { PlaceLocalMapper.mapFromDomain(it) })
    }, onError = { Log.e(Constants.PLACE_TAG, "error inserting places") })

    suspend fun clearPlaceList() = iPlaceDao.executeQuery({
        clearPlaceTable()
    }, onError = { Log.e(Constants.PLACE_TAG, "error clearing places") })
}