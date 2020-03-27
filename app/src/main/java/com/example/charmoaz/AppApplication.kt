package com.example.charmoaz

import android.app.Application
import androidx.room.Room
import com.example.charmoaz.data.DatabaseApplication

class AppApplication : Application() {

    companion object {
        lateinit var database: DatabaseApplication
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(this, DatabaseApplication::class.java, "Charmoaz")
            .fallbackToDestructiveMigrationOnDowngrade()
            .build()
    }

}
