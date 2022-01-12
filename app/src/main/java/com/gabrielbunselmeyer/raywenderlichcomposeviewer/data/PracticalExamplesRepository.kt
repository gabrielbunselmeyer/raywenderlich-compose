package com.gabrielbunselmeyer.raywenderlichcomposeviewer.data

import com.gabrielbunselmeyer.raywenderlichcomposeviewer.data.models.GitHubFileModel
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.data.models.TutorialContentModel
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.data.models.convertContentToTutorialModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class PracticalExamplesRepository {
    private val gitHubApi = Retrofit.Builder()
        .baseUrl("https://api.github.com/repos/raywenderlich/ios-interview/contents/Practical%20Example/")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PracticalExamplesApi::class.java)

    suspend fun getArticles(): TutorialContentModel = gitHubApi.getArticlesFileModel().convertContentToTutorialModel()

    suspend fun getVideos(): TutorialContentModel = gitHubApi.getVideosFileModel().convertContentToTutorialModel()
}

interface PracticalExamplesApi {
    @GET("articles.json")
    suspend fun getArticlesFileModel(): GitHubFileModel

    @GET("videos.json")
    suspend fun getVideosFileModel(): GitHubFileModel
}