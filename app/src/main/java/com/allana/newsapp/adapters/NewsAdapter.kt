package com.allana.newsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.allana.newsapp.R
import com.allana.newsapp.databinding.ItemArticlePreviewBinding
import com.allana.newsapp.models.Article
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    companion object {
        private const val fromFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        private const val toFormat = "dd-MM-yyyy HH:mm"
        private val parser = SimpleDateFormat(fromFormat, Locale.ROOT)
        private val formatter = SimpleDateFormat(toFormat, Locale.ROOT)
    }

    inner class ArticleViewHolder(val binding: ItemArticlePreviewBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            ItemArticlePreviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]

        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        val formattedDate = formatter.format(parser.parse(article.publishedAt))

        holder.binding.apply {
            Glide.with(holder.itemView)
                    .load(article.urlToImage)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                    .error(R.drawable.ic_error)
                    .into(this.ivArticleImage)

            this.tvSource.text = article.source?.name
            this.tvTitle.text = article.title
            this.tvDescription.text = article.description
            this.tvPublishedAt.text = formattedDate
        }

        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(article) }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(article: (Article) -> Unit) {
        onItemClickListener = article
    }


}