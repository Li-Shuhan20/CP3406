package com.example.androidstarter.data.remote

import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

data class OpenLibrarySearchResponse(
    @SerializedName("docs")
    val docs: List<OpenLibraryDoc> = emptyList()
)

data class OpenLibraryDoc(
    @SerializedName("title")
    val title: String? = null,

    @SerializedName("author_name")
    val authorNames: List<String>? = null
)

interface OpenLibraryApiService {
    @GET("search.json")
    suspend fun searchBooks(
        @Query("q") query: String
    ): OpenLibrarySearchResponse
}

object OpenLibraryApi {

    private const val BASE_URL = "https://openlibrary.org/"

    val service: OpenLibraryApiService by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(OpenLibraryApiService::class.java)
    }
}