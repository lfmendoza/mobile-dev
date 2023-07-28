package com.example.laboratorio1

// Clase que representa los elementos de retorno
data class ItemData(
    val originalPos: Int,
    val originalValue: Any?,
    val type: String,
    val info: String?
)

// Función processList
fun processList(inputList: List<Any>): List<ItemData>? {
    if (inputList == null) {
        return null
    }

    val result = mutableListOf<ItemData>()
    for ((index, value) in inputList.withIndex()) {
        if (value == null) {
            continue // Ignorar elementos nulos en la lista de entrada
        }

        val type = when (value) {
            is Int -> "entero"
            is String -> "cadena"
            is Boolean -> "booleano"
            else -> null
        }

        val info = when (value) {
            is Int -> {
                when {
                    value % 10 == 0 -> "M10"
                    value % 5 == 0 -> "M5"
                    value % 2 == 0 -> "M2"
                    else -> null
                }
            }
            is String -> "L${value.length}"
            is Boolean -> if (value) "Verdadero" else "Falso"
            else -> null
        }

        if (type != null) {
            result.add(ItemData(index, value, type, info))
        }
    }

    return if (result.isEmpty()) null else result
}

fun main() {
    val inputList: List<Any> = listOf(5, "Hola", true, null) as List<Any>
    val result = processList(inputList)

    result?.forEach {
        println("Pos: ${it.originalPos}, Value: ${it.originalValue}, Type: ${it.type}, Info: ${it.info}")
    }
}
