package ir.divar.data.remote

import com.google.gson.annotations.SerializedName
import ir.divar.domain.remote.BaseResult


/**
 * Represents FourSquare Places API response.
 *
 * @author Sepi 6/6/22
 */
data class ApiResponse<T : Any, V : Any>(
    val summary: T? = null,
    val results: V? = null,
    @SerializedName("errorText")
    val message: String? = null,
) {

    /**
     * Checks state of the API response and determines its type.
     */
    fun <V : Any> determineStatus(
        onSuccessData: V?
    ) = if (results != null)
        BaseResult.Success(onSuccessData)
    else BaseResult.Error(message)
}