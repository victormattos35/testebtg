package br.com.testebtg.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.testebtg.R
import br.com.testebtg.model.Film
import br.com.testebtg.model.ListFilms
import br.com.testebtg.util.formatDate
import br.com.testebtg.view.adapter.FilmsAdapter
import br.com.testebtg.viewmodel.FilmsViewModel
import kotlinx.android.synthetic.main.fragment_films.view.*
import java.util.*
import kotlin.collections.ArrayList

class FilmsFragment(private var listFilms: ListFilms) : Fragment() {

    private var listFilmsAux: ListFilms = listFilms
    private val viewModel = FilmsViewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_films, null)
        listFilms.let {
            view.rvFFilms.layoutManager =
                LinearLayoutManager(
                    activity!!.applicationContext,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            view.rvFFilms.adapter = FilmsAdapter(listFilms.results, view.context)
        }
        addTextWatcher(view)
        view.my_swipeRefresh_Layout.setOnRefreshListener {
            viewModel.requestFilms()
            viewModel.listFilms.observe(this, androidx.lifecycle.Observer {
                view.my_swipeRefresh_Layout.isRefreshing = false
            })
        }
        return view
    }

    private fun addTextWatcher(view: View) {
        view.etFFilmsSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s.let {
                    val tempArrayList = ArrayList<Film>()
                    for (item in listFilmsAux.results) {
                        if (item.title.toUpperCase(
                                Locale(
                                    "pt",
                                    "BR"
                                )
                            ).contains(s.toString().toUpperCase(Locale("pt", "BR"))) ||
                            formatDate(item.release_date).contains(s.toString())
                        ) {
                            tempArrayList.add(item)
                        }
                    }
                    view.rvFFilms.adapter = FilmsAdapter(tempArrayList, view.context)
                }
            }
        })
    }
}