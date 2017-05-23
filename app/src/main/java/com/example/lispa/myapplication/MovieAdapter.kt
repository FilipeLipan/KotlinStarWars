package com.example.lispa.myapplication

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lispa.myapplication.model.Movie
import kotlinx.android.synthetic.main.list_item.view.*
import kotlin.collections.mutableListOf

/**
 * Created by lispa on 22/05/2017.
 */
class MovieAdapter(var movies: MutableList<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    fun swapMovies(movieList: MutableList<Movie>){
        movies = movieList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val inflater = LayoutInflater.from(parent!!.context)
        val view = inflater.inflate(R.layout.list_item, parent, false)
        return MovieHolder(view)
    }

    override fun onBindViewHolder(holder: MovieHolder?, position: Int) {
        holder!!.bind(movies.get(position))
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bind(movie: Movie) = with(itemView) {
            titleMovie.text = movie.title
        }
    }
}