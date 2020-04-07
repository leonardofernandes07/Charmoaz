package com.example.charmoaz.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import br.com.hbsis.padawan.posmanagement.ui.cadastrocliente.VerificacoesTextWatcher
import com.example.charmoaz.R
import com.example.charmoaz.data.entity.Cliente
import com.example.charmoaz.databinding.ActivityCadastroClienteBinding
import com.example.charmoaz.util.Mascaras
import com.example.charmoaz.util.VerificaCampo
import kotlin.random.Random

class CadastroClienteActivity : AppCompatActivity() , MainAdapter.OnItemAction{

    private val verifica = VerificaCampo()

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


    private fun salvarCliente() {
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
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Atenção!")
        builder.setMessage("Você deseja sair sem salvar?")
        builder.setPositiveButton(getString(R.string.sair)) { _, _ ->
            finish()
        }
        builder.setNegativeButton(getString(R.string.cancelar)) { _, _ -> }
        builder.show()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.xml.fade_in, R.xml.mover_direita)
    }

    override fun onDelete(cliente: Cliente) {}

    override fun onDetail(id: Long) {

    }
}

