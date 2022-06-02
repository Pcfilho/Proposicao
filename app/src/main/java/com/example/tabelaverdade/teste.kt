package com.example.tabelaverdade


val teste = "a∨b"

private val lista2 = arrayOf('∧', '∨', '¬', '↔', '→')
private val tabelaConjuncao = arrayOf('V', 'F', 'F', 'F')
private val tabelaDisjuncao = arrayOf('V', 'V', 'V', 'F')
private val tabelaCondicional = arrayOf('V', 'F', 'V', 'V')
private val tabelaBicondicional = arrayOf('V', 'F', 'F', 'V')
private val tabelaElementoA = arrayOf('V', 'V', 'F', 'F')
private val tabelaElementoB = arrayOf('V', 'F', 'V', 'F')

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
    var i = 0
    while(i < 4){
        println("${tabelaElementoA[i]} | ${tabelaElementoB[i]} | ${response[i]}")
        i++
    }
}

fun calculate(msg: String){
    val treat = msg.toCharArray().asList()
    var count = 0
    var i = 0
    treat.forEach {
        if (lista2.contains(it)) {
            count++
        }
    }

    if (count > 0) {
        while (i < count) {
            var element1 = '.'
            var element2 = '.'
            var operator = '.'
            var last = '.'

            treat.forEach {
                if (last == '('){
                    element1 = it
                }
                last = it
            }

            last = '.'
            treat.forEach {
                if (last == element1){
                    operator = it
                }
                last = it
            }

            last = '.'
            treat.forEach {
                if (last == operator){
                    element2 = it
                }
                last = it
            }






            i++
        }
    } else {
        take(msg)
    }
}


fun main() {
    take(teste)
}