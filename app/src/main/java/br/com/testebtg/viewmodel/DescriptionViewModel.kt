package br.com.testebtg.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.testebtg.model.ListGenres
import br.com.testebtg.network.RetrofitClient
import br.com.testebtg.repository.Repository
import br.com.testebtg.util.returnLocaleString
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DescriptionViewModel : ViewModel() {

    private val retrofitClient = RetrofitClient.getRetrofitInstance()

    private val listGenres: MutableLiveData<ListGenres> = MutableLiveData()

    fun requestGenres(context: Context) {
        val request = retrofitClient.create(Repository::class.java)
        val genresRequest =
            request.getGenres(
                api_key = RetrofitClient.api_key,
                language = returnLocaleString(context)
            )
        genresRequest.enqueue(object : Callback<ListGenres> {
            override fun onResponse(call: Call<ListGenres>, response: Response<ListGenres>) {
                listGenres.postValue(response.body())
            }

            override fun onFailure(call: Call<ListGenres>, t: Throwable) {
                listGenres.postValue(null)
            }
        })
    }

    fun getListGenres(): MutableLiveData<ListGenres> {
        return listGenres
    }
}