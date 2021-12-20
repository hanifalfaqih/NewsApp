package com.allana.newsapp.repository

import com.allana.newsapp.api.ApiBuilder
import com.allana.newsapp.db.ArticleDatabase
import com.allana.newsapp.models.Article

class NewsRepository(private val db: ArticleDatabase) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        ApiBuilder.getService().getBreakingNews(countryCode, pageNumber)

    suspend fun getSearchNews(searchQuery: String, pageNumber: Int) =
        ApiBuilder.getService().getSearchNews(searchQuery, pageNumber)

    suspend fun insertArticle(article: Article) = db.getArticleDao().insertArticle(article)

    fun getAllBookmarkNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)

}