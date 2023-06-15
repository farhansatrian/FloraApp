package org.d3if3008.floraapp.network

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import org.d3if3008.floraapp.model.Tumbuhan
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = ""

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface TumbuhanApiService {
    @GET("tumbuhan-api.json")
    suspend fun getTumbuhan(): List<Tumbuhan>
}

object TumbuhanApi {
    val service: TumbuhanApiService by lazy {
        retrofit.create(TumbuhanApiService::class.java)
    }

    fun getTumbuhanUrl(ImageId: String): String {
        return "$BASE_URL$ImageId.jpg"
    }

    enum class ApiStatus { LOADING, SUCCES, FAILED}
}