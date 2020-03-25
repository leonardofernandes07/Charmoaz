package com.example.charmoaz.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import br.com.ambev.comodato.callerassinae.data.repositories.Repository
import com.example.charmoaz.R
import com.example.charmoaz.data.entity.Cliente
import com.example.charmoaz.databinding.ActivityCadastroClienteBinding
import com.example.charmoaz.util.VerificaCampo

class CadastroClienteActivity : AppCompatActivity() {

    private val verifica = VerificaCampo()
    private val repository by lazy { Repository() }

    private val binding: ActivityCadastroClienteBinding by lazy {
        DataBindingUtil.setContentView<ActivityCadastroClienteBinding>(
            this,
            R.layout.activity_cadastro_cliente
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_cliente)

    }

    fun salvarCliente(){
        if (verificaoDeCampo()){
            val cliente = Cliente(
                id = 0,
                clienteId = 1,
                clienteNome = binding.editNome.text.toString(),
                clienteCpf = binding.editCpf.text.toString(),
                clienteEmail = binding.editEmail.text.toString(),
                clienteCelular = binding.editCelular.text.toString(),
                cidade = binding.editCidade.text.toString(),
                bairro = binding.editBairro.text.toString(),
                endereco = binding.editEndereco.text.toString(),
                numero = binding.editNumero.text.toString(),
                clienteDescricao = binding.editDescricao.text.toString()
            )
            repository.insert(cliente)
            Toast.makeText(this,"Cadastro com sucesso",Toast.LENGTH_LONG).show()
            finish()
        }else{
            Toast.makeText(this,"Cadastro Inválido",Toast.LENGTH_LONG).show()
        }
    }

    fun verificaoDeCampo(): Boolean {
        return verifica.verificaVazio(binding.editNome.text.toString()) &&
                verifica.verificaCPF(binding.editCpf.text.toString()) &&
//                verifica.verificaEmail(binding.editEmail.text.toString()) &&
                verifica.verificaCelular(binding.editCelular.text.toString()) &&
                verifica.verificaVazio(binding.editCidade.text.toString()) &&
                verifica.verificaVazio(binding.editBairro.text.toString()) &&
                verifica.verificaVazio(binding.editEndereco.text.toString()) &&
                verifica.verificaNumero(binding.editNumero.text.toString()) &&
                verifica.verificaVazio(binding.editDescricao.text.toString())
    }
}