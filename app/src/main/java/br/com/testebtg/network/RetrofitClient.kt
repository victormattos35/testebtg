package br.com.testebtg.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    companion object {
        const val api_key = "9d9dd9944e73acfe8ca2dd51697af9e9"
        private const val url = "https://api.themoviedb.org/"
        private val gsonBuilder = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
                .build()
        }
    }
}