package com.example.lispa.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.*
import com.example.lispa.myapplication.model.Movie
import com.example.lispa.myapplication.retrofit.StarWarsApi
import kotlinx.android.synthetic.main.activity_main.*
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    var movies = mutableListOf<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listMainActivity.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        listMainActivity.adapter = MovieAdapter(movies, this)

        main_bt.setOnClickListener {
            Toast.makeText(this, "uhul", Toast.LENGTH_SHORT).show()
        }

        val api = StarWarsApi()

        api.loadMovies()?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribe ({ movie ->
            movies.add(movie)
        }, { e ->
            e.printStackTrace()
        },{
            listMainActivity.adapter = MovieAdapter(movies, this)
        })

//        api.loadMovies()?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribe ({ movie ->
//            movies.add("${movie.title} -- ${movie.episodeId}")
//        }, { e ->
//            e.printStackTrace()
//        },{
//            movieAdapter.notifyDataSetChanged()
//            listMainActivity.adapter = movieAdapter
//        })

//        api.loadMoviesFull()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe ({
//                    movie ->
//                    movies.add("${movie.title} -- ${movie.episodeId}\n ${movie.characters.toString() }")
//                }, {
//                    e ->
//                    e.printStackTrace()
//                },{
//                    movieAdapter?.notifyDataSetChanged()
//                })
    }
}