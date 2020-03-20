package com.example.charmoaz.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.charmoaz.data.BaseDao
import com.example.charmoaz.data.entity.Cliente

@Dao
interface ClienteDao : BaseDao<Cliente> {

    @Query("SELECT * FROM cliente")
    fun findAll(): List<Cliente>

    @Query("DELETE FROM cliente")
    fun truncate()
}