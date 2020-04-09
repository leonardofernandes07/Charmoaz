package com.example.charmoaz.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.hbsis.padawan.posmanagement.ui.cadastrocliente.VerificacoesTextWatcher
import com.example.charmoaz.R
import com.example.charmoaz.data.entity.Cliente
import com.example.charmoaz.databinding.ActivityCadastroClienteBinding
import com.example.charmoaz.util.Mascaras
import com.example.charmoaz.util.VerificaCampo
import kotlin.random.Random

class CadastroClienteActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CLIENT_ID = "EXTRA_CLIENT_ID"
    }

    private val clientId by lazy { intent.extras?.getLong(EXTRA_CLIENT_ID) }

    private val verifica = VerificaCampo()
    private var telaCadastro = true
    private var editado = false

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

        clientId?.let { onDetail(it) }

        if(telaCadastro){
            binding.titulo = "Cadastro de Cliente"
        }else{
            binding.titulo = "Informações do Cliente"
            binding.editNome.isEnabled = false
            binding.editCpf.isEnabled = false
            binding.editEmail.isEnabled = false
            binding.editCelular.isEnabled = false
            binding.editCidade.isEnabled = false
            binding.editBairro.isEnabled = false
            binding.editEndereco.isEnabled = false
            binding.editNumero.isEnabled = false
            binding.editDescricao.isEnabled = false
        }

        binding.fabEdit.setOnClickListener {
            editado = true
            binding.editNome.isEnabled = true
            binding.editCpf.isEnabled = true
            binding.editEmail.isEnabled = true
            binding.editCelular.isEnabled = true
            binding.editCidade.isEnabled = true
            binding.editBairro.isEnabled = true
            binding.editEndereco.isEnabled = true
            binding.editNumero.isEnabled = true
            binding.editDescricao.isEnabled = true
        }
        binding.fab.setOnClickListener {
            if (telaCadastro) {
                cadastrarCliente()
            } else {
                salvandoAlteracaoCliente()
            }
        }
        virificaoTw()
    }

    private fun salvandoAlteracaoCliente() {
        if (editado){
            if(verificaoDeCampo()){

            }
        }
    }


    private fun cadastrarCliente() {
        val randomValues = List(1) { Random.nextInt(0, 999) }
        if (verificaoDeCampo()) {
            val cliente = Cliente(
                id = 0,
                clienteId = 0,
                clienteNome = binding.editNome.text.toString().trim(),
                clienteCpf = binding.editCpf.text.toString().trim(),
                clienteEmail = binding.editEmail.text.toString().trim(),
                clienteCelular = binding.editCelular.text.toString().trim(),
                cidade = binding.editCidade.text.toString().trim(),
                bairro = binding.editBairro.text.toString().trim(),
                endereco = binding.editEndereco.text.toString().trim(),
                numero = binding.editNumero.text.toString().trim(),
                clienteDescricao = binding.editDescricao.text.toString().trim()
            )
            cliente.clienteId = randomValues[0].toLong()
            viewModel.saveCliente(cliente)
            Toast.makeText(applicationContext, "Cadastro Válido", Toast.LENGTH_LONG).show()
            finish()
        } else {
            Toast.makeText(applicationContext, "Cadastro Inválido", Toast.LENGTH_LONG).show()
        }
    }

    private fun verificaoDeCampo(): Boolean {
        return verifica.verificaVazio(binding.editNome.text.toString()) &&
                verifica.verificaCPF(binding.editCpf.text.toString()) &&
                verifica.verificaEmail(binding.editEmail.text.toString()) &&
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
        if (editado || telaCadastro) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Atenção!")
            builder.setMessage("Você deseja sair sem salvar?")
            builder.setPositiveButton(getString(R.string.sair)) { _, _ ->
                finish()
            }
            builder.setNegativeButton(getString(R.string.cancelar)) { _, _ -> }
            builder.show()
        }else{
            super.onBackPressed()
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.xml.fade_in, R.xml.mover_direita)
    }

    private fun onDetail(id: Long) {
        telaCadastro = false
        viewModel.selectById(id).observe(this, Observer { cliente: Cliente ->
            binding.editNome.setText(cliente.clienteNome)
            binding.editCpf.setText(cliente.clienteCpf)
            binding.editEmail.setText(cliente.clienteEmail)
            binding.editCelular.setText(cliente.clienteCelular)
            binding.editCidade.setText(cliente.cidade)
            binding.editBairro.setText(cliente.bairro)
            binding.editEndereco.setText(cliente.endereco)
            binding.editNumero.setText(cliente.numero)
            binding.editDescricao.setText(cliente.clienteDescricao)
        })
    }
}


