package `in`.flyferry.firebaaseotpverification.Repository

import `in`.flyferry.firebaaseotpverification.API.NewsService
import `in`.flyferry.firebaaseotpverification.Models.ArticleList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class NewsRepository (val newService: NewsService) {

    private val articleList = MutableLiveData<ArticleList>()

    val articles : LiveData<ArticleList>
    get() = articleList

    suspend fun getNews(q:String, from : String, to : String, sortBy : String, apiKey : String){
        val response = newService.getNews(q,from,to,sortBy,apiKey)

        if (response.body()!=null){
            articleList.postValue(response.body())
        }
    }

}