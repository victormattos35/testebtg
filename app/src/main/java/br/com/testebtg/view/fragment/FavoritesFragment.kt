package br.com.testebtg.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.testebtg.R
import br.com.testebtg.model.Favorites
import br.com.testebtg.model.Film
import br.com.testebtg.model.ListFilms
import br.com.testebtg.util.formatDate
import br.com.testebtg.view.adapter.FilmsAdapter
import br.com.testebtg.viewmodel.FavoritesViewModel
import br.com.testebtg.viewmodel.FilmsViewModel
import kotlinx.android.synthetic.main.activity_list_films.*
import kotlinx.android.synthetic.main.fragment_films_and_favorites.view.*
import java.util.*
import kotlin.collections.ArrayList


class FavoritesFragment(private var listFilms: ListFilms) : Fragment() {

    private val viewModelFilms = FilmsViewModel()
    private val viewModelFavorites = FavoritesViewModel()
    private var listFilmsFilter = ArrayList<Film>()
    private var listFilmsFavorites = ArrayList<Film>()
    private var pbLFLoading: ProgressBar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_films_and_favorites, null)
        pbLFLoading = activity!!.findViewById(R.id.pbFFLoading)
        listFilms.results.map { if (it.isFavorite) listFilmsFilter.add(it) }
        listFilmsFilter.let {
            view.rvFFFilms.layoutManager =
                LinearLayoutManager(
                    activity!!.applicationContext,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            listFilmsFavorites = listFilmsFilter
            view.rvFFFilms.adapter = FilmsAdapter(listFilmsFilter, view.context)
        }
        setEventFields(view)
        initializeObservable(view)
        return view
    }

    private fun initializeObservable(view: View) {
        viewModelFilms.listFilms.observe(this, androidx.lifecycle.Observer {
            try {
                listFilmsFilter = ArrayList(it.results)
                viewModelFavorites.getAllFavorites(activity!!.applicationContext)
            } catch (e: Exception) {
                pbLFLoading?.visibility = View.GONE
                view.srlFFRefresh.isRefreshing = false
                Toast.makeText(
                    activity!!.applicationContext,
                    "Não foi possível atualizar a lista",
                    Toast.LENGTH_LONG
                ).show()
                e.printStackTrace()
            }
        })

        viewModelFavorites.listFavorites.observe(this, androidx.lifecycle.Observer {
            listFilmsFavorites.clear()
            it.map { favorites -> favorites.id = 0 }
            listFilmsFilter.map { film ->
                film.isFavorite = it.contains(Favorites(0, film.id))
                if (film.isFavorite) listFilmsFavorites.add(film)
            }
            view.srlFFRefresh.isRefreshing = false
            pbLFLoading?.visibility = View.GONE
            view.rvFFFilms.adapter = FilmsAdapter(listFilmsFavorites, view.context)
        })
    }

    private fun setEventFields(view: View) {
        view.etFFFilmsSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(textSearch: String?): Boolean {
                var listFilmsSearch = ArrayList<Film>()
                textSearch.let {
                    for (item in listFilmsFavorites) {
                        if (item.title.toUpperCase(
                                Locale(
                                    "pt",
                                    "BR"
                                )
                            ).contains(textSearch.toString().toUpperCase(Locale("pt", "BR"))) ||
                            formatDate(item.release_date).contains(textSearch.toString())
                        ) {
                            listFilmsSearch.add(item)
                        }
                    }
                    view.rvFFFilms.adapter = FilmsAdapter(listFilmsSearch, view.context)
                }
                return false
            }

        })

        view.srlFFRefresh.setOnRefreshListener {
            viewModelFilms.requestFilms()
        }
    }

    override fun onResume() {
        pbLFLoading?.visibility = View.VISIBLE
        viewModelFilms.requestFilms()
        super.onResume()
    }
}