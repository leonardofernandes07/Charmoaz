package com.example.charmoaz.util

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText

object Mascaras {

    fun unmask(s: String): String {
        return s.replace("[.]".toRegex(), "").replace("[-]".toRegex(), "")
            .replace("[/]".toRegex(), "").replace("[(]".toRegex(), "")
            .replace("[ ]".toRegex(), "").replace("[)]".toRegex(), "")
    }

    fun insert(type: MaskType, ediTxt: TextInputEditText): TextWatcher {
        return object : TextWatcher {
            var isUpdating = false
            var old = ""
            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (isUpdating) {
                    isUpdating = false
                    old = s.toString()
                    return
                }
                if (!s.toString().isEmpty() && s.toString().length > old.length) {
                    val str = unmask(s.toString())
                    var mask = ""
                    var i = 0
                    for (m in type.mask.toCharArray()) {
                        if (m != '#') {
                            mask += m
                            continue
                        }
                        mask += try {
                            str[i]
                        } catch (e: Exception) {
                            break
                        }
                        i++
                    }
                    isUpdating = true
                    ediTxt.setText(mask)
                    ediTxt.setSelection(mask.length)
                } else {
                    old = s.toString()
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {}
        }
    }

    /**
     * Médoto para setar os tipos de mascaras
     */
    enum class MaskType(var mask: String) {
         CPF("###.###.###-##"), TEL("(##) # ####-####");

    }
}