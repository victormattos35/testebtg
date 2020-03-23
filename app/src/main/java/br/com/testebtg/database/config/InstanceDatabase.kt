package br.com.testebtg.database.config

import android.content.Context
import androidx.room.Room

class InstanceDatabase {
    companion object {
        fun getDatabase(context: Context): DatabaseConfig {
            return Room.databaseBuilder(
                context,
                DatabaseConfig::class.java,
                "movieDB")
                .allowMainThreadQueries()
                .build()
        }
    }
}