package com.example.charmoaz.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.hbsis.padawan.posmanagement.ui.cadastrocliente.VerificacoesTextWatcher
import com.example.charmoaz.R
import com.example.charmoaz.data.entity.Cliente
import com.example.charmoaz.databinding.ActivityCadastroClienteBinding
import com.example.charmoaz.util.Mascaras
import com.example.charmoaz.util.VerificaCampo

class CadastroClienteActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CLIENT_ID = "EXTRA_CLIENT_ID"
    }

    private val clientId by lazy { intent.extras?.getLong(EXTRA_CLIENT_ID) }
    private val verifica = VerificaCampo()

    private val viewModel: ViewModelCadastro by lazy {
        ViewModelProvider(this).get(ViewModelCadastro::class.java)
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

        viewModel.setClientId(clientId)

        if (clientId == null) {
            binding.titulo = "Cadastro de Cliente"
            binding.fabEdit.isEnabled = false
            binding.fabEdit.isVisible = false
        } else {
            binding.titulo = "Informações do Cliente"
        }

        viewModel.cliente.observe(this, Observer {
            binding.cliente = it
        })

        viewModel.isEditing.observe(this, Observer {
            binding.editNome.isEnabled = it
            binding.editCpf.isEnabled = it
            binding.editEmail.isEnabled = it
            binding.editCelular.isEnabled = it
            binding.editCidade.isEnabled = it
            binding.editBairro.isEnabled = it
            binding.editEndereco.isEnabled = it
            binding.editNumero.isEnabled = it
            binding.editDescricao.isEnabled = it
        })

        viewModel.finished.observe(this, Observer {
            if (it)
                finish()
        })

        binding.fabEdit.setOnClickListener {
            viewModel.isEditing()
        }

        binding.fab.setOnClickListener {
            salvaCliente()
        }
        verificaoTextwatcher()
    }


    private fun salvaCliente() {
        if (verificaoDeCampo()) {
            val cliente = viewModel.cliente.value!!.copy()

            cliente.apply {
                clienteNome = binding.editNome.text.toString().trim()
                clienteCpf = binding.editCpf.text.toString().trim()
                clienteEmail = binding.editEmail.text.toString().trim()
                clienteCelular = binding.editCelular.text.toString().trim()
                cidade = binding.editCidade.text.toString().trim()
                bairro = binding.editBairro.text.toString().trim()
                endereco = binding.editEndereco.text.toString().trim()
                numero = binding.editNumero.text.toString().trim()
                clienteDescricao = binding.editDescricao.text.toString().trim()
            }
            viewModel.saveCliente(cliente)
        } else {
            Toast.makeText(applicationContext, "Cadastro Inválido", Toast.LENGTH_LONG).show()
        }
    }

    private fun verificaoDeCampo(): Boolean {
        var camposOk = true
        if (!verifica.verificaVazio(binding.editNome.text.toString())) {
            binding.layoutNome.error = "${binding.editNome.hint} Vazio"
            binding.layoutNome.isErrorEnabled = true
            camposOk = false
        }
        if (!verifica.verificaCPF(binding.editCpf.text.toString())) {
            binding.layoutCpf.error = "${binding.editCpf.hint} Vazio"
            binding.layoutCpf.isErrorEnabled = true
            camposOk = false
        }
        if (!verifica.verificaEmail(binding.editEmail.text.toString())) {
            binding.layoutEmail.error = "${binding.editEmail.hint} Vazio"
            binding.layoutEmail.isErrorEnabled = true
            camposOk = false
        }
        if (!verifica.verificaCelular(binding.editCelular.text.toString())) {
            binding.layoutCelular.error = "${binding.editCelular.hint} Vazio"
            binding.layoutCelular.isErrorEnabled = true
            camposOk = false
        }
        if (!verifica.verificaVazio(binding.editCidade.text.toString())) {
            binding.layoutCidade.error = "${binding.editCidade.hint} Vazio"
            binding.layoutCidade.isErrorEnabled = true
            camposOk = false
        }
        if (!verifica.verificaVazio(binding.editBairro.text.toString())) {
            binding.layoutBairro.error = "${binding.editBairro.hint} Vazio"
            binding.layoutBairro.isErrorEnabled = true
            camposOk = false
        }
        if (!verifica.verificaVazio(binding.editEndereco.text.toString())) {
            binding.layoutEndereco.error = "${binding.editEndereco.hint} Vazio"
            binding.layoutEndereco.isErrorEnabled = true
            camposOk = false
        }
        if (!verifica.verificaNumero(binding.editNumero.text.toString())) {
            binding.layoutNumero.error = "${binding.editNumero.hint} Vazio"
            binding.layoutNumero.isErrorEnabled = true
            camposOk = false
        }
        if (!verifica.verificaDesc(binding.editDescricao.text.toString())) {
            binding.layoutDescricao.error = "${binding.editDescricao} Vazio"
            binding.layoutDescricao.isErrorEnabled = true
            camposOk = false
        }
        return camposOk
    }

    private fun verificaoTextwatcher() {
        setMask()
        binding.editNome.addTextChangedListener(VerificacoesTextWatcher(binding.layoutNome))
        binding.editCpf.addTextChangedListener(VerificacoesTextWatcher(binding.layoutCpf))
        binding.editEmail.addTextChangedListener(VerificacoesTextWatcher(binding.layoutEmail))
        binding.editCelular.addTextChangedListener(
            VerificacoesTextWatcher(
                binding.layoutCelular
            )
        )
        binding.editCidade.addTextChangedListener(
            VerificacoesTextWatcher(
                binding.layoutCidade
            )
        )
        binding.editBairro.addTextChangedListener(
            VerificacoesTextWatcher(
                binding.layoutBairro
            )
        )
        binding.editEndereco.addTextChangedListener(
            VerificacoesTextWatcher(
                binding.layoutEndereco
            )
        )
        binding.editNumero.addTextChangedListener(
            VerificacoesTextWatcher(
                binding.layoutNumero
            )
        )
        binding.editDescricao.addTextChangedListener(
            VerificacoesTextWatcher(
                binding.layoutDescricao
            )
        )
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

    override fun onBackPressed() {
        if (viewModel.isEditing.value == true) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Atenção!")
            builder.setMessage("Você deseja sair sem salvar?")
            builder.setPositiveButton(getString(R.string.sair)) { _, _ ->
                finish()
            }
            builder.setNegativeButton(getString(R.string.cancelar)) { _, _ -> }
            builder.show()
        } else {
            super.onBackPressed()
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.xml.fade_in, R.xml.mover_direita)
    }
}


