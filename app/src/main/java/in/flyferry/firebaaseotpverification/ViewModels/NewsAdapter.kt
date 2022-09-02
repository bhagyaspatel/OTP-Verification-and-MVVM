package `in`.flyferry.firebaaseotpverification.ViewModels

import `in`.flyferry.firebaaseotpverification.Models.Article
import `in`.flyferry.firebaaseotpverification.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter (val context : Context, private val articles : List<Article>) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    inner class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.title)
        val author = itemView.findViewById<TextView>(R.id.author)
        val description = itemView.findViewById<TextView>(R.id.description)
        val image = itemView.findViewById<ImageView>(R.id.newsImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.newsview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = articles[position]
        holder.title.text = currentItem.title
        holder.author.text = currentItem.author
        holder.description.text = currentItem.description
        Glide.with(context).load(currentItem.urlToImage).into(holder.image)
    }

    override fun getItemCount(): Int {
        return articles.size
    }
}