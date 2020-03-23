package br.com.testebtg.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.testebtg.model.Favorites

@Dao
interface FavoritesDAO {
    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): List<Favorites>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorites: Favorites): Long

    @Query("DELETE FROM favorites WHERE idFilm = :idFilm")
    fun deleteByIdFilm(idFilm: Int)
}