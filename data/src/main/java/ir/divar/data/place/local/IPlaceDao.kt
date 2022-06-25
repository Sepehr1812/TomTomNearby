package ir.divar.data.place.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ir.divar.data.db.GeneralDao
import ir.divar.data.place.local.model.PlaceEntity


@Dao
interface IPlaceDao : GeneralDao {

    @Query("SELECT * FROM Places LIMIT 10 OFFSET :offset")
    suspend fun getPlaceListFromLocal(offset: Int): List<PlaceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaceList(placeList: List<PlaceEntity>)

    @Query("DELETE FROM Places")
    suspend fun clearPlaceTable()
}