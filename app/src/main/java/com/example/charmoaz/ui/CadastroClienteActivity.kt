package com.example.charmoaz.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.charmoaz.R
import com.example.charmoaz.databinding.ActivityMainBinding
import com.example.charmoaz.util.VerificaCampo
import kotlinx.android.synthetic.main.activity_cadastro_cliente.*

class CadastroClienteActivity : AppCompatActivity() {

    private val verifica = VerificaCampo()

    private val layoutNome by lazy { layout_nome }
    private val layoutCpf by lazy { layout_cpf }
    private val layoutEmail by lazy { layout_email }
    private val layoutCelular by lazy { layout_celular }
    private val layoutCidade by lazy { layout_cidade }
    private val layoutBairro by lazy { layout_bairro }
    private val layoutEndereco by lazy { layout_endereco }
    private val layoutNumero by lazy { layout_numero }
    private val layoutDescricao by lazy { layout_descricao }

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(
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
            Toast.makeText(this,"Cadastro com sucesso",Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this,"Cadastro Inválido",Toast.LENGTH_LONG).show()
        }
    }

    fun verificaoDeCampo(): Boolean {
        return verifica.verificaVazio(layoutNome.editText?.text.toString()) &&
                verifica.verificaCPF(layoutCpf.editText?.text.toString()) &&
                verifica.verificaEmail(layoutEmail.editText?.text.toString()) &&
                verifica.verificaCelular(layoutCelular.editText?.text.toString()) &&
                verifica.verificaVazio(layoutCidade.editText?.text.toString()) &&
                verifica.verificaVazio(layoutBairro.editText?.text.toString()) &&
                verifica.verificaVazio(layoutEndereco.editText?.text.toString()) &&
                verifica.verificaNumero(layoutNumero.editText?.text.toString()) &&
                verifica.verificaVazio(layoutDescricao.editText?.text.toString())
    }
}