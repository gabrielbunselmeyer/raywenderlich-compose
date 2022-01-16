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
        .client(OkHttpClient())
//        .client(OkHttpClient.Builder().addInterceptor(GitHubAuthInterceptor()).build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PracticalExamplesApi::class.java)

    suspend fun getArticles(): TutorialContentModel =
        gitHubApi.getArticlesFileModel().convertContentToTutorialModel()

    suspend fun getVideos(): TutorialContentModel =
        gitHubApi.getVideosFileModel().convertContentToTutorialModel()

    /**
     * The GitHubAPI has a relatively low rate limit if you're not authenticated; 60 requests per hour I believe.
     * This interceptor can be used to authenticate through GitHub. It's commented out as it might not be needed
     * if you won't do any heavy testing.
     * To authenticate, you can use your username and access token, as detailed here:
     * https://docs.github.com/en/rest/guides/getting-started-with-the-rest-api#authentication
     * For security reasons I'm not leaving my own access token in and have since deleted it.
     *
     * You'll also need to uncomment the [OkHttpClient] call in like 17 (in the Retrofit Builder).
     */
//    private class GitHubAuthInterceptor : Interceptor {
//        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
//            val request = chain.request()
//            val authenticatedRequest = request
//                .newBuilder()
//                .header(
//                    "Authorization",
//                    Credentials.basic(
//                        "gabrielbunselmeyer",
//                        ACCESS_TOKEN
//                    )
//                )
//                .build()
//
//            return chain.proceed(authenticatedRequest)
//        }
//    }
}

interface PracticalExamplesApi {
    @GET("articles.json")
    suspend fun getArticlesFileModel(): GitHubFileModel

    @GET("videos.json")
    suspend fun getVideosFileModel(): GitHubFileModel
}