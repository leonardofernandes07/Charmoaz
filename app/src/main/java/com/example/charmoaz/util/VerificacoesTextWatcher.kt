package br.com.hbsis.padawan.posmanagement.ui.cadastrocliente

import android.text.Editable
import android.text.TextWatcher
import com.example.charmoaz.R
import com.example.charmoaz.data.entity.Cliente
import com.example.charmoaz.util.VerificaCampo
import com.google.android.material.textfield.TextInputLayout

class VerificacoesTextWatcher : TextWatcher {
    private var layoutText: TextInputLayout? = null
    private val verificaCampo: VerificaCampo = VerificaCampo()
    private var campoVerificado = true

    constructor(layoutText: TextInputLayout?) {
        this.layoutText = layoutText
    }

    override fun beforeTextChanged(
        s: CharSequence,
        start: Int,
        count: Int,
        after: Int
    ) {
    }

    override fun onTextChanged(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
    ) {
        if (layoutText!!.editText!!.text.toString() == "") {
            layoutText!!.error = "Preencha Campo"
        } else {
            layoutText!!.isErrorEnabled = false
            when (layoutText!!.editText!!.id) {
                R.id.edit_cpf-> {
                        campoVerificado =
                            verificaCampo.verificaCPF(layoutText!!.editText!!.text.toString())
                        if (campoVerificado) {
                            layoutText!!.isErrorEnabled = false
                        } else {
                            layoutText!!.error = layoutText!!.editText!!.hint.toString() +
                                    " Incompleto"
                        }
                }
                R.id.edit_numero -> {
                    campoVerificado =
                        verificaCampo.verificaNumero(layoutText!!.editText!!.text.toString())
                    if (campoVerificado) {
                        layoutText!!.isErrorEnabled = false
                    } else {
                        layoutText!!.error = layoutText!!.editText!!.hint.toString() +
                                " Incompleto"
                    }
                }
                R.id.edit_celular -> {
                    campoVerificado =
                        verificaCampo.verificaCelular(layoutText!!.editText!!.text.toString())
                    if (campoVerificado) {
                        layoutText!!.isErrorEnabled = false
                    } else {
                        layoutText!!.error = layoutText!!.editText!!.hint.toString() +
                                " Incompleto"
                    }
                }
            }
        }
    }

    override fun afterTextChanged(s: Editable) {}
}