package br.com.testebtg.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.testebtg.model.ListFilms
import br.com.testebtg.network.RetrofitClient
import br.com.testebtg.repository.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilmsViewModel : ViewModel() {
    private val retrofitClient = RetrofitClient.getRetrofitInstance()

    val listFilms: MutableLiveData<ListFilms> = MutableLiveData()

    fun requestFilms() {
        val request = retrofitClient.create(Repository::class.java)
        val filmsRequest =
            request.getPopularFilms(api_key = RetrofitClient.api_key)
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