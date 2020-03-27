package com.example.charmoaz.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.charmoaz.data.dao.ClienteDao
import com.example.charmoaz.data.entity.Cliente

@Database(
    entities = [
        Cliente::class
    ], version = 1, exportSchema = false
)
abstract class DatabaseApplication : RoomDatabase() {
    abstract fun clienteDao(): ClienteDao
}
