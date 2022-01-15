package com.gabrielbunselmeyer.raywenderlichcomposeviewer.data

import com.gabrielbunselmeyer.raywenderlichcomposeviewer.data.model.GitHubFileModel
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.data.model.TutorialContentModel
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.data.model.convertContentToTutorialModel
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class PracticalExamplesRepository {
    private val gitHubApi = Retrofit.Builder()
        .baseUrl("https://api.github.com/repos/raywenderlich/ios-interview/contents/Practical%20Example/")
        .client(OkHttpClient.Builder().addInterceptor(GitHubAuthInterceptor()).build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PracticalExamplesApi::class.java)

    suspend fun getArticles(): TutorialContentModel =
        gitHubApi.getArticlesFileModel().convertContentToTutorialModel()

    suspend fun getVideos(): TutorialContentModel =
        gitHubApi.getVideosFileModel().convertContentToTutorialModel()

    // The GitHubAPI has a relatively low rate limit if you're not authenticated.
    // This interceptor uses simple authentication through passing in my GitHub user. No PW needed.
    private class GitHubAuthInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val request = chain.request()
            val authenticatedRequest = request
                .newBuilder()
                .header(
                    "Authorization",
                    Credentials.basic(
                        "gabrielbunselmeyer",
                        "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"
                    )
                )
                .build()

            return chain.proceed(authenticatedRequest)
        }
    }
}

interface PracticalExamplesApi {
    @GET("articles.json")
    suspend fun getArticlesFileModel(): GitHubFileModel

    @GET("videos.json")
    suspend fun getVideosFileModel(): GitHubFileModel
}