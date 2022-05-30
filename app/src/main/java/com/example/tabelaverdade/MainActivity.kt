package com.example.tabelaverdade

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.tabelaverdade.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private var text = ""
    private val lista1 = arrayOf('∧', '∨', '¬', '↔', '→', '(', ')')
    private val lista2 = arrayOf('∧', '∨', '¬', '↔', '→')

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

        }
    }

    override fun onClick(v: View?) {
        with(binding) {
            when(v) {
                btnA -> text += 'A'
                btnB -> text += 'B'
                btnC -> text += 'C'
                btnD -> text += 'D'
                btnConjuncao -> text += '∧'
                btnDisjuncao -> text += '∨'
                btnImplicacao -> text += '→'
                btnEquivalencia -> text += '↔'
                btnParenteseStart -> text += '('
                btnParenteseEnd -> text += ')'
                btnResult -> response()
                btnClear -> clearAll()
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
                    if (lista2.contains(char)) {
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
}