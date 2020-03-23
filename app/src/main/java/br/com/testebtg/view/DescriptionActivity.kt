package br.com.testebtg.view

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.testebtg.R
import br.com.testebtg.model.Film
import br.com.testebtg.util.getImageUrl
import br.com.testebtg.viewmodel.DescriptionViewModel
import br.com.testebtg.viewmodel.FavoritesViewModel
import kotlinx.android.synthetic.main.activity_description.*

class DescriptionActivity : AppCompatActivity() {

    private var film: Film? = null
    private val viewModelDescription: DescriptionViewModel = DescriptionViewModel()
    private val viewModelFavorites: FavoritesViewModel = FavoritesViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        init()
    }

    private fun init() {
        getExtras()
        setValues()
        setEventFields()
        initializeObservable()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setEventFields() {
        ivDBackButton.setOnClickListener {
            finish()
        }

        ibDFavorite.setOnClickListener {
            film.let {
                film?.isFavorite = !film?.isFavorite!!
                if (film?.isFavorite!!) {
                    viewModelFavorites.insertFilmInFavorite(this, film!!)
                    ibDFavorite.setImageDrawable(applicationContext.getDrawable(R.drawable.ic_star_gold_24dp))
                } else {
                    viewModelFavorites.deleteFilmInFavorite(this, film!!)
                    ibDFavorite.setImageDrawable(applicationContext.getDrawable(R.drawable.ic_star_border_gold_24dp))
                }
            }
        }
    }

    private fun initializeObservable() {
        viewModelDescription.requestGenres()
        viewModelDescription.listGenres.observe(this, Observer {
            try {
                var listGenres = ""
                it.genres.filter { genre -> film!!.genre_ids.contains(genre.id) }.also { genres ->
                    genres.forEach { itemGenre ->
                        listGenres += itemGenre.name + ", "
                    }
                }
                tvDGenres.text = listGenres.substring(0, listGenres.length - 2)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setValues() {
        getImageUrl(this, film?.backdrop_path, ivDImageFilm)
        tvDTitleFilm.text = film?.title
        tvDTextDescription.text = film?.overview
        rvDVoteAverage.rating = (film?.vote_average?.div(2))?.toFloat() ?: 0F
        if (film?.isFavorite!!) {
            ibDFavorite.setImageDrawable(applicationContext.getDrawable(R.drawable.ic_star_gold_24dp))
        } else {
            ibDFavorite.setImageDrawable(applicationContext.getDrawable(R.drawable.ic_star_border_gold_24dp))
        }
    }

    private fun getExtras() {
        film = intent.extras?.get("film") as Film
    }
}