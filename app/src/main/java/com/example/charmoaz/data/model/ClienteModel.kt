package br.com.ambev.comodato.callerassinae.data.model

import com.example.charmoaz.data.entity.Cliente
import com.example.charmoaz.data.entity.Endereco
import com.example.charmoaz.formatIntBrazil

data class ClienteModel(
    val clienteId: Int,
    val clienteNome: String,
    val clienteCpf: String,
    val clienteEmail: String,
    val clienteCelular: String,
    val enderecoId: Int,
    val cidade: String,
    val bairro: String,
    val endereco: String,
    val numero: Int,
    val clienteDescricao: String
) {

    val cliente: Cliente = Cliente(
        id = 0,
        clienteId = 0,
        clienteNome = clienteNome,
        clienteCpf = clienteCpf,
        clienteEmail = clienteEmail,
        clienteCelular = clienteCelular,
        enderecoId = enderecoId,
        numero = numero,
        clienteDescricao = clienteDescricao
    )

    val end: Endereco = Endereco(
        id = 0,
        cidade = cidade,
        bairro = bairro,
        endereco = endereco
    )


    companion object {
        fun create(line: Array<out String>): ClienteModel {
            return ClienteModel(
                clienteId = line[0].formatIntBrazil(),
                clienteNome = line[1].trim(),
                clienteCpf = line[2].trim(),
                clienteEmail = line[3].trim(),
                clienteCelular = line[4].trim(),
                enderecoId = line[5].formatIntBrazil(),
                cidade = line[6].trim(),
                bairro = line[7].trim(),
                endereco = line[8].trim(),
                numero = line[9].formatIntBrazil(),
                clienteDescricao = line[10].trim()

            )
        }
    }

}
