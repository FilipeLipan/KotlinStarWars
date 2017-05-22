package com.example.lispa.myapplication.retrofit

import com.example.lispa.myapplication.model.FilmResult
import com.example.lispa.myapplication.model.Person
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

/**
 * Created by lispa on 21/05/2017.
 */
interface StarWarsApiDef {
    @GET("films")
    fun listMovies() : Observable<FilmResult>

    @GET("people/{personId}")
    fun loadPerson(@Path("personId") personId : String) : Observable<Person>
}