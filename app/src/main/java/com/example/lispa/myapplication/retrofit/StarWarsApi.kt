package com.example.lispa.myapplication.retrofit

import android.net.Uri
import com.example.lispa.myapplication.model.Character
import com.example.lispa.myapplication.model.Movie
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable

/**
 * Created by lispa on 21/05/2017.
 */
class StarWarsApi {
    val service: StarWarsApiDef

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val gson = GsonBuilder().setLenient().create()

        val retrofit = Retrofit.Builder()
                .baseUrl("http://swapi.co/api/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build()

        service = retrofit.create<StarWarsApiDef>(StarWarsApiDef::class.java)
    }

    fun loadMovies(): Observable<Movie>? {
        return service.listMovies()
                .flatMap { filmResults -> Observable.from(filmResults.results) }
                .map { film ->
                    Movie(film.title, film.episodeId, ArrayList<Character>())
                }
    }

    fun loadMoviesFull(): Observable<Movie> {
        return service.listMovies()
                .flatMap { filmResults -> Observable.from(filmResults.results) }
                .flatMap { film ->
                    Observable.zip(
                            Observable.just(Movie(film.title, film.episodeId, ArrayList<Character>())),
                            Observable.from(film.personUrls)
                                    .flatMap { personUrl ->
                                        service.loadPerson(Uri.parse(personUrl).lastPathSegment)
                                    }
                                    .map { person ->
                                        Character(person!!.name, person.gender)
                                    }
                                    .toList(),
                            { movie, characters ->
                                movie.characters.addAll(characters)
                                movie
                            })
                }
    }
}