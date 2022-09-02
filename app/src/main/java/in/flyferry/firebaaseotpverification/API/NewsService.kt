package `in`.flyferry.firebaaseotpverification.API

import `in`.flyferry.firebaaseotpverification.Models.ArticleList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService  {

    @GET("/v2/everything")
    suspend fun getNews(@Query("q")q:String,
                        @Query("from")from:String,
                        @Query("to")to:String,
                        @Query("sortBy")sortBy:String,
                        @Query("apiKey")apiKey:String,
    )
    : Response<ArticleList>
}