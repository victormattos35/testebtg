package br.com.testebtg.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.testebtg.database.config.InstanceDatabase
import br.com.testebtg.model.Favorites
import br.com.testebtg.model.Film
import br.com.testebtg.model.ListGenres

class FavoritesViewModel : ViewModel() {
    private val listFavorites: MutableLiveData<List<Favorites>> = MutableLiveData()

    fun insertFilmInFavorite(context: Context, film: Film) {
        var favorite = Favorites(0, film.id)
        val databaseRoom = InstanceDatabase.getDatabase(context)
        databaseRoom.favoritesDao().insert(favorite)
    }

    fun deleteFilmInFavorite(context: Context, film: Film) {
        val databaseRoom = InstanceDatabase.getDatabase(context)
        databaseRoom.favoritesDao().deleteByIdFilm(film.id)
    }

    fun getAllFavorites(context: Context) {
        val databaseRoom = InstanceDatabase.getDatabase(context)
        listFavorites.postValue(databaseRoom.favoritesDao().getAllFavorites())
    }

    fun getListFavorites(): MutableLiveData<List<Favorites>> {
        return listFavorites
    }
}