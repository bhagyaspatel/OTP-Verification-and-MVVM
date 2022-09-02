package `in`.flyferry.firebaaseotpverification.Activity

import `in`.flyferry.firebaaseotpverification.API.NewsService
import `in`.flyferry.firebaaseotpverification.API.RetorfitHelper
import `in`.flyferry.firebaaseotpverification.R
import `in`.flyferry.firebaaseotpverification.Repository.NewsRepository
import `in`.flyferry.firebaaseotpverification.ViewModels.MainViewModel
import `in`.flyferry.firebaaseotpverification.ViewModels.MainViewModelFactory
import `in`.flyferry.firebaaseotpverification.databinding.ActivityHomeBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import `in`.flyferry.firebaaseotpverification.ViewModels.NewsAdapter

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding
    private val TAG = "Home_Activity"

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter : NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }

        val newsService = RetorfitHelper.getInstance().create(NewsService::class.java)
        val repository = NewsRepository(newsService)
        viewModel = ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)

        viewModel.news.observe(this) {  articleList->
            Log.d(TAG, "inside view modal observer")
            if (articleList != null){
                binding.progressBar.visibility = View.GONE
                binding.newsRV.visibility = View.VISIBLE
                Log.d(TAG, "API call successful $articleList")
                adapter = NewsAdapter(this@HomeActivity, articleList.articles)
                binding.newsRV.adapter = adapter
                binding.newsRV.layoutManager = LinearLayoutManager(this@HomeActivity)
            }else {
                Log.d(TAG, "API call successful but quotes is null")
            }
        }
    }
}