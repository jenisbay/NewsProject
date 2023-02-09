package com.example.newsproject.features.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsproject.R
import com.example.newsproject.data.models.Article
import com.example.newsproject.features.home.OnItemClickListener
import javax.inject.Inject

class SearchNewsAdapter @Inject constructor() : RecyclerView.Adapter<SearchNewsAdapter.SearchNewsViewHolder>() {

    class SearchNewsViewHolder(view: View): RecyclerView.ViewHolder(view){
        val image: ImageView = view.findViewById(R.id.imageView)
        val title: TextView = view.findViewById(R.id.tvTitle)
        val date: TextView = view.findViewById(R.id.tvDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchNewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_news_item, parent, false)
        return SearchNewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchNewsViewHolder, position: Int) {
        val article = differ.currentList[position]

        Glide.with(holder.itemView).load(article.urlToImage).placeholder(R.drawable.news_placeholder).into(holder.image)
        holder.title.text = article.title
        holder.date.text = article.publishedAt
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(article.url)
            }
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setData(data: List<Article>){
        differ.submitList(data)
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener){
        onItemClickListener = listener
    }


}