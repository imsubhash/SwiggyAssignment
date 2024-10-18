package com.example.swiggyassignment.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.swiggyassignment.R
import com.example.swiggyassignment.data.Search
import com.example.swiggyassignment.databinding.MovieListItemBinding
import com.example.swiggyassignment.util.Constants.MOVIE_ID
import com.example.swiggyassignment.view.MovieDetailsActivity

class MoviePagingAdapter : PagingDataAdapter<Search, MoviePagingAdapter.MyViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val search = getItem(position)
        search?.let {
            holder.bind(it)
        }
    }

    class MyViewHolder(private val binding: MovieListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(search: Search) {
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, MovieDetailsActivity::class.java)
                intent.putExtra(MOVIE_ID, search.imdbID)
                itemView.context.startActivity(intent)
            }
            Glide.with(itemView.context)
                .load(search.Poster)
                .placeholder(R.drawable.placeholder_loading)
                .error(R.drawable.broken_image)
                .into(binding.poster)

            binding.title.text = search.Title
            binding.year.text = search.Year
            binding.type.text = search.Type
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Search>() {
            override fun areItemsTheSame(oldItem: Search, newItem: Search): Boolean {
                return oldItem.imdbID == newItem.imdbID
            }

            override fun areContentsTheSame(oldItem: Search, newItem: Search): Boolean {
                return oldItem == newItem
            }
        }
    }

}