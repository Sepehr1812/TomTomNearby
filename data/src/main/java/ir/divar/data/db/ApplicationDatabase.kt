package ir.divar.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.divar.data.place.local.model.PlaceEntity


@Database(entities = [PlaceEntity::class], version = 1)
abstract class ApplicationDatabase : RoomDatabase()