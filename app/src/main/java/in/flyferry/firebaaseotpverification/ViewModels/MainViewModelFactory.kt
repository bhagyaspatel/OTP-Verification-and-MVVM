package `in`.flyferry.firebaaseotpverification.ViewModels

import `in`.flyferry.firebaaseotpverification.Repository.NewsRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory(private val newsRepository: NewsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(newsRepository) as T
    }
}