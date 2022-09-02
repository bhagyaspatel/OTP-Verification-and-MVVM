package `in`.flyferry.firebaaseotpverification.Models

data class ArticleList(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)