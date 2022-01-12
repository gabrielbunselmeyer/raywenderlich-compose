package com.gabrielbunselmeyer.raywenderlichcomposeviewer.data

import com.gabrielbunselmeyer.raywenderlichcomposeviewer.data.models.GitHubFileModel
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object PracticalExamplesAdapter {
    val practicalExamplesClient: PracticalExamplesClient = Retrofit.Builder()
        .baseUrl("https://api.github.com/repos/raywenderlich/ios-interview/contents/Practical%20Example/")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PracticalExamplesClient::class.java)
}

interface PracticalExamplesClient {

    @GET("articles.json")
    suspend fun getArticlesFileModel(): Response<GitHubFileModel>

    @GET("videos.json")
    suspend fun getVideosFileModel(): Response<GitHubFileModel>
}