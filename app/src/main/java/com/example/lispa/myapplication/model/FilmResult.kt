package com.example.lispa.myapplication.model

import com.google.gson.annotations.SerializedName

/**
 * Created by lispa on 21/05/2017.
 */
data class FilmResult(val results : List<Film>)

data class Film (val title : String,
                 @SerializedName("episode_id")
                 val episodeId : Int,
                 @SerializedName("characters")
                 val personUrls : List<String>)

data class Person(val name : String,
                  val gender : String)