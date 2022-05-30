package com.example.tabelaverdade


val teste = "a∧b"

private val lista2 = arrayOf('∧', '∨', '¬', '↔', '→')
private val tabelaConjuncao = arrayOf('F', 'F', 'F', 'V')
private val tabelaDisjuncao = arrayOf('F', 'V', 'V', 'V')
private val tabelaCondicional = arrayOf('V', 'V', 'F', 'V')
private val tabelaBicondicional = arrayOf('V', 'F', 'F', 'V')

fun take(msg: String) {
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
}



fun main() {
    take(teste)
}