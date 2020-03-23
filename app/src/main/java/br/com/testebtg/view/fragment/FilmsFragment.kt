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

class FilmsFragment(private var listFilms: ListFilms) : Fragment() {

    private val viewModelFilm = FilmsViewModel()
    private val viewModelFavorites = FavoritesViewModel()
    private var listFilmsRefresh = ArrayList<Film>()

    private var pbLFLoading: ProgressBar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_films_and_favorites, null)
        pbLFLoading = activity!!.findViewById(R.id.pbFFLoading)
        listFilms.let {
            view.rvFFFilms.layoutManager =
                LinearLayoutManager(
                    activity!!.applicationContext,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            view.rvFFFilms.adapter = FilmsAdapter(listFilms.results, view.context)
        }
        listFilmsRefresh = listFilms.results as ArrayList<Film>
        setEventFields(view)
        initializeObservable(view)
        pbLFLoading?.visibility = View.GONE
        return view
    }

    private fun initializeObservable(view: View) {
        viewModelFilm.listFilms.observe(this, androidx.lifecycle.Observer {
            try {
                listFilmsRefresh = ArrayList(it.results)
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
            it.map { favorites -> favorites.id = 0 }
            view.srlFFRefresh.isRefreshing = false
            listFilmsRefresh.map { film ->
                film.isFavorite = it.contains(Favorites(0, film.id))
            }
            view.srlFFRefresh.isRefreshing = false
            pbLFLoading?.visibility = View.GONE
            view.rvFFFilms.adapter = FilmsAdapter(listFilmsRefresh, view.context)
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
                    for (item in listFilmsRefresh) {
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
            viewModelFilm.requestFilms()
        }
    }

    override fun onResume() {
        pbLFLoading?.visibility = View.VISIBLE
        viewModelFilm.requestFilms()
        super.onResume()
    }
}