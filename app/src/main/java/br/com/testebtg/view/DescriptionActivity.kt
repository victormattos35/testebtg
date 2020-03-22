package br.com.testebtg.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.testebtg.R
import br.com.testebtg.model.Film
import br.com.testebtg.util.getImageUrl
import br.com.testebtg.viewmodel.DescriptionViewModel
import kotlinx.android.synthetic.main.activity_description.*

class DescriptionActivity : AppCompatActivity() {

    private var film: Film? = null
    private val viewModel: DescriptionViewModel = DescriptionViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        init()
    }

    private fun init() {
        initializeObservable()
        getExtras()
        setValues()
    }

    private fun initializeObservable() {
        viewModel.requestGenres()
        viewModel.listGenres.observe(this, Observer {
            var listGenres = ""
            it.genres.filter { genre -> film!!.genre_ids.contains(genre.id) }.also { genres ->
                genres.forEach { itemGenre ->
                    listGenres += itemGenre.name + ", "
                }
            }
            tvDescriptionGenres.text = listGenres.substring(0, listGenres.length - 2)
        })
    }

    private fun setValues() {
        getImageUrl(this, film?.backdrop_path, ivDescriptionImageFilm)
        tvDescriptionTitleFilm.text = film?.title
        tvDescriptionTextDescription.text = film?.overview
        rvDescriptionVoteAverage.rating = (film?.vote_average?.div(2))?.toFloat() ?: 0F
    }

    private fun getExtras() {
        film = intent.extras?.get("film") as Film
    }
}