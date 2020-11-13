package com.example.hackernewsreader

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class RecyclerAdapter(private val news: ArrayList<News>, var listener: NewsClickListener? = null) : RecyclerView.Adapter<RecyclerAdapter.NewsHolder>() {
    interface NewsClickListener {
        fun newsClicked(news: News?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.NewsHolder {
        val inflatedView = parent.inflate(R.layout.recyclerview_item_row, false)
        return NewsHolder(inflatedView)
    }

    override fun getItemCount() = news.size

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        holder.title.text = news[position].title
        holder.url.text = news[position].url

        holder.root.setOnClickListener { listener?.newsClicked(news[position]) }
    }

    class NewsHolder(v: View) : RecyclerView.ViewHolder(v) {
        var title = v.findViewById<TextView>(R.id.newsTitle)
        var url = v.findViewById<TextView>(R.id.newsUrl)
        var root = v.findViewById<ConstraintLayout>(R.id.newsContainer)

        private var news: News? = null
    }
}