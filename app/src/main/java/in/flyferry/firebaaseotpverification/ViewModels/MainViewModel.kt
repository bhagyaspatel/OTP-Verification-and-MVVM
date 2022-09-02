package `in`.flyferry.firebaaseotpverification.ViewModels

import `in`.flyferry.firebaaseotpverification.Models.ArticleList
import `in`.flyferry.firebaaseotpverification.Repository.NewsRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    private val API_KEY = "b4a851d17a694b7cb0764a92388b4194"

    init{
        viewModelScope.launch (Dispatchers.IO){
            newsRepository.getNews("apple", "2022-09-01", "2022-09-01", "popularity", API_KEY)
        }
    }

    val news : LiveData<ArticleList>
    get() = newsRepository.articles
}