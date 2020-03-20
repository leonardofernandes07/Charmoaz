package com.example.charmoaz.data.entity

import androidx.room.*

@Entity(
    tableName = "cliente",
    indices = [
        Index(value = ["endereco_id"], name = "idx_invoices_01")
    ],
    foreignKeys = [ForeignKey(
        entity = Endereco::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("endereco_id"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Cliente @JvmOverloads constructor(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    @ColumnInfo(name = "cliente_Id")
    var clienteId: Long,
    @ColumnInfo(name = "cliente_Nome")
    var clienteNome: String,
    @ColumnInfo(name = "cliente_cpf")
    var clienteCpf: String,
    @ColumnInfo(name = "cliente_email")
    var clienteEmail: String,
    @ColumnInfo(name = "cliente_celular")
    var clienteCelular: String,
    @ColumnInfo(name = "endereco_id")
    var enderecoId: Int,
    @ColumnInfo(name = "numero")
    var numero: Int,
    @ColumnInfo(name = "cliente_Descricao")
    var clienteDescricao: String

)
