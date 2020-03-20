package com.example.charmoaz

import android.app.Application
import androidx.room.Room
import com.example.charmoaz.data.AppDatabase

class AppApplication : Application() {

    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "Charmoaz"
            ).fallbackToDestructiveMigration()
            .build()
    }

}
