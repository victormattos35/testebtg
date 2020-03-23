package br.com.testebtg.repository

import br.com.testebtg.model.ListFilms
import br.com.testebtg.model.ListGenres
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Repository {

    @GET("/3/movie/popular")
    fun getPopularFilms(@Query("api_key") api_key: String, @Query("language") language: String): Call<ListFilms>

    @GET("/3/genre/movie/list")
    fun getGenres(@Query("api_key") api_key: String, @Query("language") language: String): Call<ListGenres>
}