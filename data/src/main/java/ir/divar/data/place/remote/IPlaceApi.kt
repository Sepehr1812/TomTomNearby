package ir.divar.data.place.remote

import ir.divar.data.place.remote.model.PlaceRemoteModel
import ir.divar.data.place.remote.model.PlaceSummaryRemoteModel
import ir.divar.data.remote.ApiResponse
import ir.divar.data.remote.GeneralApi
import ir.divar.interviewtask.data.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface IPlaceApi : GeneralApi {

    @GET("search/2/nearbySearch/.json")
    suspend fun getPlaces(
        @Query("lat") latitude: Float,
        @Query("lon") longitude: Float,
        @Query("ofs") offset: Int,
        @Query("radius") radius: Int = 5000, // 5 km = 5000 meters
        @Query("key") key: String = BuildConfig.TOMTOM_AUTHORIZATION_TOKEN
    ): ApiResponse<PlaceSummaryRemoteModel, List<PlaceRemoteModel>>
}