package ir.divar.data.place.remote.model

import ir.divar.domain.place.model.Place
import ir.divar.domain.place.model.PlaceSummaryResponse
import kotlin.math.roundToInt

object PlaceRemoteMapper {

    fun mapToDomain(placeRemoteModel: PlaceRemoteModel) = placeRemoteModel.run {
        Place(
            id,
            placeInfo.name,
            convertCategoryListToString(placeInfo.categories),
            distance.roundToInt(),
            position,
            address
        )
    }

    fun mapSummaryToDomain(placeSummaryRemoteModel: PlaceSummaryRemoteModel) =
        placeSummaryRemoteModel.run {
            PlaceSummaryResponse(offset, totalResults)
        }

    private fun convertCategoryListToString(categories: List<String>): String {
        var result = ""
        categories.forEach {
            result =
                result.plus(
                    // capitalize each word
                    it.trim().split(" ")
                        .joinToString(" ") { s -> s.replaceFirstChar(Char::titlecase) })
                    .plus(", ")
        }

        return result.removeSuffix(", ")
    }
}