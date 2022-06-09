package ir.divar.data.place.remote.model

import ir.divar.domain.place.model.Place
import ir.divar.domain.place.model.PlaceCategory

object PlaceRemoteMapper {

    fun mapToDomain(placeRemoteModel: PlaceRemoteModel) = placeRemoteModel.run {
        Place(id, name, convertCategoryListToString(categories), distance, geocodes.main, location)
    }

    private fun convertCategoryListToString(categories: List<PlaceCategory>): String {
        var result = ""
        categories.forEach {
            result = result.plus(it.name).plus(", ")
        }

        return result.removeSuffix(", ")
    }
}