package test.ahmed.uc.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import test.ahmed.uc.model.MainResponse

interface Service {

    @GET("all-sections/7.json")
    suspend fun getData(
        @Query("api-key") key: String?,
        @Header("App-ID") appId: String?,
        @Header("Password") password: String?,
        @Header("Content-Type") type: String?,
        @Header("Lang") lang: String?,
        @Header("DeviceType") deviceType: String
    ): Response<MainResponse>?
}