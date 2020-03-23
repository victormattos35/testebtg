package br.com.testebtg.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.testebtg.model.ListFilms
import br.com.testebtg.network.RetrofitClient
import br.com.testebtg.repository.Repository
import br.com.testebtg.util.returnLocaleString
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilmsViewModel : ViewModel() {
    private val retrofitClient = RetrofitClient.getRetrofitInstance()

    val listFilms: MutableLiveData<ListFilms> = MutableLiveData()

    fun requestFilms(context: Context) {
        val request = retrofitClient.create(Repository::class.java)
        val filmsRequest =
            request.getPopularFilms(
                api_key = RetrofitClient.api_key,
                language = returnLocaleString(context)
            )
        filmsRequest.enqueue(object : Callback<ListFilms> {
            override fun onResponse(call: Call<ListFilms>, response: Response<ListFilms>) {
                listFilms.postValue(response.body())
            }

            override fun onFailure(call: Call<ListFilms>, t: Throwable) {
                listFilms.postValue(null)
            }
        })
    }

}