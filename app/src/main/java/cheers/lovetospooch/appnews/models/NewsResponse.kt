package cheers.lovetospooch.appnews.models

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)