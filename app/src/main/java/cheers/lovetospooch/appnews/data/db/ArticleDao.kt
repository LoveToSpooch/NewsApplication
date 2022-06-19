package cheers.lovetospooch.appnews.data.db

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cheers.lovetospooch.appnews.models.Article

// данный интерфейс будет получать все наши статьи, вставлять или удалять статью

interface ArticleDao {

    @Query("SELECT * FROM ")
    suspend fun getAllArticles(): LiveData<List<Article>>

    // onConflict в случае если возникает конфликт статей - мы заменяем статью
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    @Delete
    suspend fun delete(article: Article)
}