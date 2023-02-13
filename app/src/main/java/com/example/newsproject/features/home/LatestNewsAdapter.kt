package com.example.newsproject.features.home

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
import javax.inject.Inject



class LatestNewsAdapter @Inject constructor() : RecyclerView.Adapter<LatestNewsAdapter.LatestNewsViewHolder>() {

    class LatestNewsViewHolder(view: View): RecyclerView.ViewHolder(view){
        val cardViewImage: ImageView = view.findViewById(R.id.cardViewImage)
        val title: TextView = view.findViewById(R.id.tvTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestNewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.latest_news_item, parent, false)
        return LatestNewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: LatestNewsViewHolder, position: Int) {
        val article = differ.currentList[position]

        Glide.with(holder.itemView).load(article.urlToImage).placeholder(R.drawable.news_placeholder).into(holder.cardViewImage)
        holder.title.text = article.title
        holder.cardViewImage.setOnClickListener {
            onItemClickListener?.let {
                it(article)
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

    fun setOnClickListener(listener: OnItemClickListener){
        onItemClickListener = listener
    }
}


typealias OnItemClickListener = (Article) -> Unit
