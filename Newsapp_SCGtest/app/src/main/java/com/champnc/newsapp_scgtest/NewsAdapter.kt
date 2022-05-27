package com.champnc.newsapp_scgtest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.champnc.newsapp_scgtest.databinding.CardNewsBinding
import com.champnc.newsapp_scgtest.model.Article
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

class NewsAdapter(private var list: List<Article>, private val listener: (Article) -> Unit) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardNewsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], listener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(newList: List<Article>) {
        list = newList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: CardNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article, listener: (Article) -> Unit) {
            Glide.with(binding.root.context).load(article.urlToImage)
                .apply(RequestOptions().override(1000, 1000).placeholder(R.drawable.ic_placeholder))
                .fitCenter().into(binding.ivNews)
            binding.tvTitle.text = article.title
            binding.tvDesc.text = article.content
            binding.tvDate.text = article.publishedAt!!.dateFromISOtoSimpleUTC()

            binding.root.setOnClickListener {
                listener.invoke(article)
            }
        }
    }
}
