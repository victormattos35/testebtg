package br.com.testebtg.database.config

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.testebtg.database.FavoritesDAO
import br.com.testebtg.model.Favorites

@Database(entities = [Favorites::class], version = 1, exportSchema = false)
abstract class DatabaseConfig : RoomDatabase(){
    abstract fun favoritesDao(): FavoritesDAO
}