package com.example.charmoaz.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.ambev.comodato.callerassinae.data.repositories.Repository
import com.example.charmoaz.R
import com.example.charmoaz.data.entity.Cliente
import com.example.charmoaz.databinding.ActivityCadastroClienteBinding
import com.example.charmoaz.util.Mascaras
import com.example.charmoaz.util.VerificaCampo
import com.example.charmoaz.util.VerificacoesTextWatcher
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.Exception

class CadastroClienteActivity : AppCompatActivity() {

    private val verifica = VerificaCampo()
    private val repository by lazy { Repository() }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private val binding: ActivityCadastroClienteBinding by lazy {
        DataBindingUtil.setContentView<ActivityCadastroClienteBinding>(
            this,
            R.layout.activity_cadastro_cliente
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_cliente)

        binding.fab.setOnClickListener {
            salvarCliente()
        }
        virificaoTw()
    }

    @SuppressLint("LongLogTag")
    private fun salvarCliente() {
        viewModel.clienteList.observe(this, Observer {
            try {
                if (verificaoDeCampo()) {
                    Log.e("Campo Verificado", verificaoDeCampo().toString())
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
                    Log.e("Inseriu", repository.getAll().toString())
                    Toast.makeText(this, "Cadastro com sucesso", Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(this, "Cadastro inválido", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
            }
        })
    }

    private fun verificaoDeCampo(): Boolean {
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

    private fun virificaoTw() {
        setMask()
        binding.editNome.addTextChangedListener(VerificacoesTextWatcher(binding.layoutNome))
        binding.editCpf.addTextChangedListener(VerificacoesTextWatcher(binding.layoutCpf))
        binding.editEmail.addTextChangedListener(VerificacoesTextWatcher(binding.layoutEmail))
        binding.editCelular.addTextChangedListener(VerificacoesTextWatcher(binding.layoutCelular))
        binding.editCidade.addTextChangedListener(VerificacoesTextWatcher(binding.layoutCidade))
        binding.editBairro.addTextChangedListener(VerificacoesTextWatcher(binding.layoutBairro))
        binding.editEndereco.addTextChangedListener(VerificacoesTextWatcher(binding.layoutEndereco))
        binding.editNumero.addTextChangedListener(VerificacoesTextWatcher(binding.layoutNumero))
        binding.editDescricao.addTextChangedListener(VerificacoesTextWatcher(binding.layoutDescricao))
    }

    private fun setMask() {
        binding.editCelular.addTextChangedListener(
            Mascaras.insert(
                Mascaras.MaskType.TEL,
                binding.editCelular
            )
        )

        binding.editCpf.addTextChangedListener(
            Mascaras.insert(
                Mascaras.MaskType.CPF,
                binding.editCpf
            )
        )
    }
}
