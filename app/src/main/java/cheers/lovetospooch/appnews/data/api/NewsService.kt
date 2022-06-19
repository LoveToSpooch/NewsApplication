package cheers.lovetospooch.appnews.data.api

import cheers.lovetospooch.appnews.models.NewsResponse
import cheers.lovetospooch.appnews.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET

import retrofit2.http.Query

interface NewsService {

    // создадим два endpoint - типа запросы, которые покажут нам либо все, либо популярные
    // /v2/everything - это запросы с сайта с api

    @GET("/v2/everything")
    suspend fun getEverything(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsResponse>

    @GET("/v2/top-headlines")
    suspend fun getHeadlines(
        @Query("country") countryCode: String = "ru",
        @Query("page") page: Int = 1,
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsResponse>
}