package com.example.charmoaz.util

class VerificaCampo {

    fun verificaVazio(campo: String): Boolean {
        return campo != ""
    }

    fun verificaEmail(campo: String): Boolean {
        return campo.contains("@") && campo.contains(".") || campo.isEmpty()
    }


    fun verificaNumero(campo: String): Boolean {
        return !(campo.length >= 6 || campo == "0" || campo == "" || campo == "00" || campo == "000"
                    || campo == "0000" || campo == "000000")
    }

    fun verificaCPF(campo: String): Boolean {
        return campo.length == 14 || campo.isEmpty()
    }

    fun verificaCelular(campo: String): Boolean {
        return campo.length == 16
    }
}
