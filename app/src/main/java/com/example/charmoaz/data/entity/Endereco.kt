package com.example.charmoaz.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "endereco")
data class Endereco @JvmOverloads constructor(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    @ColumnInfo(name = "cidade")
    var cidade: String,
    @ColumnInfo(name = "bairro")
    var bairro: String,
    @ColumnInfo(name = "endereco")
    var endereco: String,
    @Ignore
    var clientes: Set<Cliente> = setOf()
)


