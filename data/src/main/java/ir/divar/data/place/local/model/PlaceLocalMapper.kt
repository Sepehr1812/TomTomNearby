package ir.divar.data.place.local.model

import ir.divar.domain.place.model.Place

object PlaceLocalMapper {

    fun mapToDomain(placeEntity: PlaceEntity) = placeEntity.run {
        Place(id, name, categories, distance, geocode, address)
    }

    fun mapFromDomain(place: Place) = place.run {
        PlaceEntity(id, name, categories, distance, geocode, address)
    }
}