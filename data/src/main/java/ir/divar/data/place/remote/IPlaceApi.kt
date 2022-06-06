package ir.divar.data.place.remote

import ir.divar.data.place.remote.model.PlaceRemoteModel
import ir.divar.data.remote.ApiResponse
import ir.divar.data.remote.GeneralApi
import ir.divar.interviewtask.data.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.Url

interface IPlaceApi : GeneralApi {

    @Headers(
        "Accept: application/json",
        "Authorization: ${BuildConfig.FSQ_AUTHORIZATION_TOKEN}"
    )
    @GET("places/search")
    fun getPlaces(
        @Query("ll") location: String,
        @Query("radius") radius: Int = 5000, // 5 km = 5000 meters
        @Query("client_id") clientId: String = BuildConfig.FSQ_CLIENT_ID,
        @Query("client_secret") clientSecret: String = BuildConfig.FSQ_CLIENT_SECRET,
        @Query("v") version: String = "20220601" // the date determines the version of the API
    ): ApiResponse<List<PlaceRemoteModel>>

    @Headers(
        "Accept: application/json",
        "Authorization: ${BuildConfig.FSQ_AUTHORIZATION_TOKEN}"
    )
    @GET("places/search")
    fun getPlacesByLink(
        @Url url: String
    ): ApiResponse<List<PlaceRemoteModel>>
}