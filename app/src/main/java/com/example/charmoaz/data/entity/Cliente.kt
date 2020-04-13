package com.example.charmoaz.data.entity

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "cliente")
data class Cliente @JvmOverloads constructor(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @ColumnInfo(name = "cliente_Nome")
    var clienteNome: String = "",
    @ColumnInfo(name = "cliente_cpf")
    var clienteCpf: String = "",
    @ColumnInfo(name = "cliente_email")
    var clienteEmail: String = "",
    @ColumnInfo(name = "cliente_celular")
    var clienteCelular: String = "",
    @ColumnInfo(name = "cidade")
    var cidade: String = "",
    @ColumnInfo(name = "bairro")
    var bairro: String = "",
    @ColumnInfo(name = "endereco")
    var endereco: String = "",
    @ColumnInfo(name = "numero")
    var numero: String = "",
    @ColumnInfo(name = "cliente_Descricao")
    var clienteDescricao: String = ""
)
