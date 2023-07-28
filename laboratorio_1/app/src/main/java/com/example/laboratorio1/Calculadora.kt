package com.example.laboratorio1
import kotlin.math.pow
import kotlin.math.sqrt

// Interfaz para representar operaciones matemáticas básicas
interface Operacion {
    fun calcular(num1: Double, num2: Double): Double
}

// Clase que implementa la operación de suma
class Suma : Operacion {
    override fun calcular(num1: Double, num2: Double): Double {
        return num1 + num2
    }
}

// Clase que implementa la operación de resta
class Resta : Operacion {
    override fun calcular(num1: Double, num2: Double): Double {
        return num1 - num2
    }
}

// Clase que implementa la operación de multiplicación
class Multiplicacion : Operacion {
    override fun calcular(num1: Double, num2: Double): Double {
        return num1 * num2
    }
}

// Clase que implementa la operación de división
class Division : Operacion {
    override fun calcular(num1: Double, num2: Double): Double {
        if (num2 == 0.0) {
            throw IllegalArgumentException("No se puede dividir por cero.")
        }
        return num1 / num2
    }
}

fun main() {
    val calculadora = Calculadora()
    calculadora.ejecutar()
}

class Calculadora {
    // Lista para almacenar las operaciones disponibles
    private val operaciones = listOf(
        Suma(),
        Resta(),
        Multiplicacion(),
        Division()
    )

    fun ejecutar() {
        println("Bienvenido a la Calculadora!")

        do {
            mostrarMenu()
            val opcion = readLine()?.toIntOrNull()

            if (opcion in 1..operaciones.size + 2) {
                when (opcion) {
                    operaciones.size + 1 -> calcularRaizCuadrada()
                    operaciones.size + 2 -> calcularPotencia()
                    else -> opcion?.minus(1)?.let { realizarOperacion(it) }
                }
            } else {
                println("Opción inválida. Por favor, elige una opción válida.")
            }

        } while (opcion != operaciones.size + 3)
    }

    private fun mostrarMenu() {
        println("Elige una operación:")
        for ((index, operacion) in operaciones.withIndex()) {
            println("${index + 1}. ${operacion.javaClass.simpleName}")
        }
        println("${operaciones.size + 1}. Raíz Cuadrada")
        println("${operaciones.size + 2}. Potencia")
        println("${operaciones.size + 3}. Salir")
    }

    private fun realizarOperacion(opcion: Int) {
        println("Ingresa el primer número:")
        val num1 = readLine()?.toDoubleOrNull()

        println("Ingresa el segundo número:")
        val num2 = readLine()?.toDoubleOrNull()

        if (num1 == null || num2 == null) {
            println("Números inválidos. Por favor, intenta nuevamente.")
            return
        }

        try {
            val operacion = operaciones[opcion]
            val resultado = operacion.calcular(num1, num2)
            println("El resultado es: $resultado")
        } catch (e: IllegalArgumentException) {
            println(e.message)
        }
    }

    private fun calcularRaizCuadrada() {
        println("Ingresa el número:")
        val num = readLine()?.toDoubleOrNull()

        if (num == null) {
            println("Número inválido. Por favor, intenta nuevamente.")
            return
        }

        if (num < 0) {
            println("No se puede calcular la raíz cuadrada de un número negativo.")
            return
        }

        val resultado = sqrt(num)
        println("La raíz cuadrada de $num es: $resultado")
    }

    private fun calcularPotencia() {
        println("Ingresa la base:")
        val base = readLine()?.toDoubleOrNull()

        println("Ingresa el exponente:")
        val exponente = readLine()?.toDoubleOrNull()

        if (base == null || exponente == null) {
            println("Base o exponente inválido. Por favor, intenta nuevamente.")
            return
        }

        val resultado = base.pow(exponente)
        println("El resultado de $base elevado a la $exponente es: $resultado")
    }
}
