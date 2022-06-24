package ir.divar.data.place.local.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.divar.domain.place.model.PlaceAddress
import ir.divar.domain.place.model.PlacePosition


@Entity(tableName = "Places")
data class PlaceEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val categories: String,
    val distance: Int,
    @Embedded
    val geocode: PlacePosition,
    @Embedded
    val address: PlaceAddress
)