package br.com.testebtg.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.testebtg.model.ListGenres
import br.com.testebtg.network.RetrofitClient
import br.com.testebtg.repository.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DescriptionViewModel : ViewModel() {

    private val retrofitClient = RetrofitClient.getRetrofitInstance()

    val listGenres: MutableLiveData<ListGenres> = MutableLiveData()

    fun requestGenres() {
        val request = retrofitClient.create(Repository::class.java)
        val genresRequest =
            request.getGenres(api_key = RetrofitClient.api_key, language = "pt-BR")
        genresRequest.enqueue(object : Callback<ListGenres> {
            override fun onResponse(call: Call<ListGenres>, response: Response<ListGenres>) {
                listGenres.postValue(response.body())
            }
            override fun onFailure(call: Call<ListGenres>, t: Throwable) {
                listGenres.postValue(null)
            }
        })
    }
}