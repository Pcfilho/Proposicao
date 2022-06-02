package com.example.tabelaverdade

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.tabelaverdade.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private var text = ""
    private val lista1 = arrayOf('∧', '∨', '¬', '↔', '→', '(', ')')
    private val lista2 = arrayOf('∧', '∨', '¬', '↔', '→')
    private val tabelaConjuncao = arrayOf('V', 'F', 'F', 'F')
    private val tabelaDisjuncao = arrayOf('V', 'V', 'V', 'F')
    private val tabelaCondicional = arrayOf('V', 'F', 'V', 'V')
    private val tabelaBicondicional = arrayOf('V', 'F', 'F', 'V')
    private val tabelaElementoA = arrayOf('V', 'V', 'F', 'F')
    private val tabelaElementoB = arrayOf('V', 'F', 'V', 'F')

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            btnA.setOnClickListener(this@MainActivity)
            btnB.setOnClickListener(this@MainActivity)
            btnC.setOnClickListener(this@MainActivity)
            btnD.setOnClickListener(this@MainActivity)
            btnConjuncao.setOnClickListener(this@MainActivity)
            btnDisjuncao.setOnClickListener(this@MainActivity)
            btnImplicacao.setOnClickListener(this@MainActivity)
            btnEquivalencia.setOnClickListener(this@MainActivity)
            btnResult.setOnClickListener(this@MainActivity)
            btnParenteseStart.setOnClickListener(this@MainActivity)
            btnParenteseEnd.setOnClickListener(this@MainActivity)
            btnClear.setOnClickListener(this@MainActivity)
            btnTruthTable.setOnClickListener(this@MainActivity)
        }
    }

    override fun onClick(v: View?) {
        with(binding) {
            when(v) {
                btnA -> text += 'A'
                btnB -> text += 'B'
                btnC -> text += 'C'
                btnD -> text += '¬'
                btnConjuncao -> text += '∧'
                btnDisjuncao -> text += '∨'
                btnImplicacao -> text += '→'
                btnEquivalencia -> text += '↔'
                btnParenteseStart -> text += '('
                btnParenteseEnd -> text += ')'
                btnResult -> response()
                btnClear -> clearAll()
                btnTruthTable -> take(text)
                else -> {}
            }
        }
        changeText()
    }

    private fun response() {
        val response = checkAll(text)
        if (text.isEmpty()){
            binding.tvRespone.text = "Digite algo!"
        } else {
            binding.tvRespone.text = response
        }
    }

    private fun changeText() {
        with(binding) {
            tvCalculo.text = text
        }
        if (text.length > 8){
            binding.tvCalculo.textSize
        }
    }

    private fun clearAll(){
        text = ""
        binding.tvRespone.text = ""
    }

    private fun check(msg: String) : Boolean {
        val treat = msg.toCharArray().asList()
        treat.forEach{
            if (it.isLetter() || lista1.contains(it)) {
            } else {
                return false
            }
        }
        return true
    }

    private fun check2(msg: String): Boolean {
        val response = check(msg)
        if(!response){
            return false
        }

        val treat = msg.toCharArray().asList()
        var result = 0
        var result2 = 0
        var last = '.'
        println(treat)


        treat.forEach{ char ->
            if(char.isLetter()){
                result++
            } else if (char == '¬' || char == '('|| char == ')'){
            } else {
                result--
            }
            if(result > 1 || result < 0) {
                return false
            }

            if(char == '(') {
                result2++
            } else if (char == ')') {
                result2--
            }

            if(result2 > 1 || result2 < 0) {
                return false
            }

            when{
                last == ')' -> {
                    if (char.isLetter()){
                        return false
                    }
                }
                last.isLetter() -> {
                    if (char.isLetter()) {
                        return false
                    }
                }
                lista2.contains(last) -> {
                    if (lista2.contains(char) && char != '¬') {
                        return false
                    }
                    if (char == ')'){
                        return false
                    }
                }
            }
            last = char
        }
        Toast.makeText(this, "$result e $result2", Toast.LENGTH_LONG).show()
        if (result != 1 || result2 != 0) {
            return false
        }
        return true
    }

    private fun checkAll(msg: String): String {
        val response1 = check(msg)

        if (!response1){
            return "A operação não possui componentes validos!"
        }

        val response2 = check2(msg)

        if(!response2) {
            return "Operação incorreta"
        }
        return "Operação Correta"
    }

    private fun take(msg: String) {
        if (check2(text)) {
            var response = arrayOf('.')
            val treat = msg.toCharArray().asList()
            var operator = '.'
            var element1 = '.'
            var element2 = '.'
            var last = '.'

            treat.forEach {
                if (it.isLetter() && !element1.isLetter()) {
                    element1 = it
                }
            }

            treat.forEach {
                if (last == element1) {
                    operator = it
                }
                last = it
            }

            treat.forEach {
                if (last == operator) {
                    element2 = it
                }
                last = it
            }


            when(operator) {
                '∧' -> response = tabelaConjuncao
                '∨' -> response = tabelaDisjuncao
                '→' -> response = tabelaCondicional
                '↔' -> response = tabelaBicondicional
            }

            println("$element1 e $element2 com $operator = ${response.contentToString()} de $treat")
            var result = ""
            var i = 0
            result += "$element1 | $element2 | $element1$operator$element2 \n"
            while(i < 4){
                result += ("${tabelaElementoA[i]} | ${tabelaElementoB[i]} | ${response[i]} \n")
                i++
            }

            AlertDialog.Builder(this)
                .setTitle("Tabela Verdade:")
                .setMessage(result)
                .setCancelable(true)
                .create()
                .show()
        } else {
            AlertDialog.Builder(this)
                .setTitle("Tabela Verdade:")
                .setMessage("Operação Incorreta.")
                .setCancelable(true)
                .create()
                .show()
        }

    }
}