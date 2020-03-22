package br.com.testebtg.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.testebtg.R
import br.com.testebtg.model.ListFilms
import br.com.testebtg.view.adapter.FragmentsAdapter
import br.com.testebtg.viewmodel.FilmsViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_list_films.*

class FilmsActivity : AppCompatActivity() {

    private val viewModel = FilmsViewModel()
    private var listFilms: ListFilms = ListFilms()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_films)
        init()
    }

    private fun init() {
        initializeViewModel()
        initializeTabs()
    }

    private fun initializeViewModel() {
        viewModel.requestFilms()
        viewModel.listFilms.observe(this, Observer { listFilms ->
            this.listFilms = listFilms
            initializeViewPager()
            setActionTab()
        })
    }

    private fun initializeTabs() {
        tlLFFilms.addTab(tlLFFilms.newTab().setText(R.string.test_btg_films))
        tlLFFilms.addTab(tlLFFilms.newTab().setText(R.string.test_btg_favorites))
    }

    private fun initializeViewPager() {
        val adapter = tlLFFilms?.tabCount?.let { tlFilms ->
            FragmentsAdapter(
                supportFragmentManager,
                tlFilms,
                listFilms
            )
        }

        vpLFFilms?.adapter = adapter
        vpLFFilms?.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tlLFFilms))
    }

    private fun setActionTab() {
        tlLFFilms.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    vpLFFilms?.currentItem = tab.position
                }
            }
        })
    }
}