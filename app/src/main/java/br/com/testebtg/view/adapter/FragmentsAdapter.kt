package br.com.testebtg.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import br.com.testebtg.model.ListFilms
import br.com.testebtg.view.fragment.FavoritesFragment
import br.com.testebtg.view.fragment.FilmsFragment

class FragmentsAdapter(
    fm: FragmentManager,
    private var totalTabs: Int,
    private val listFilms: ListFilms
) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                FilmsFragment(listFilms)
            }
            1 -> {
                FavoritesFragment(listFilms)
            }
            else -> FilmsFragment(listFilms)
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}