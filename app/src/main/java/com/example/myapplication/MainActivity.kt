package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var tvExpression: TextView
    private lateinit var tvResult: TextView

    private var firstNumber = 0.0
    private var secondNumber = 0.0
    private var operator = ""

    private var isNewNumber = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvExpression = findViewById(R.id.tvExpression)
        tvResult = findViewById(R.id.tvResult)

        val numberClickListener = View.OnClickListener { v ->
            val b = v as Button
            val digit = b.text.toString()

            val currentText = tvExpression.text.toString()

            if (currentText == "0" && digit != ".") {
                tvExpression.text = digit
            } else {
                tvExpression.append(digit)
            }

            isNewNumber = false
        }

        findViewById<Button>(R.id.btn0).setOnClickListener(numberClickListener)
        findViewById<Button>(R.id.btn1).setOnClickListener(numberClickListener)
        findViewById<Button>(R.id.btn2).setOnClickListener(numberClickListener)
        findViewById<Button>(R.id.btn3).setOnClickListener(numberClickListener)
        findViewById<Button>(R.id.btn4).setOnClickListener(numberClickListener)
        findViewById<Button>(R.id.btn5).setOnClickListener(numberClickListener)
        findViewById<Button>(R.id.btn6).setOnClickListener(numberClickListener)
        findViewById<Button>(R.id.btn7).setOnClickListener(numberClickListener)
        findViewById<Button>(R.id.btn8).setOnClickListener(numberClickListener)
        findViewById<Button>(R.id.btn9).setOnClickListener(numberClickListener)
        findViewById<Button>(R.id.btnDot).setOnClickListener(numberClickListener)

        val operatorClickListener = View.OnClickListener { v ->
            val b = v as Button
            operator = b.text.toString()

            firstNumber = parseDoubleSafe(tvExpression.text.toString())

            tvExpression.append(" $operator ")

            isNewNumber = false
        }

        findViewById<Button>(R.id.btnAdd).setOnClickListener(operatorClickListener)
        findViewById<Button>(R.id.btnSubtract).setOnClickListener(operatorClickListener)
        findViewById<Button>(R.id.btnMultiply).setOnClickListener(operatorClickListener)
        findViewById<Button>(R.id.btnDivide).setOnClickListener(operatorClickListener)

                // ==== Botão "=" (igual) ====
        findViewById<Button>(R.id.btnEquals).setOnClickListener {
            // Precisamos pegar qual é o segundo número
            val text = tvExpression.text.toString()

            var result = firstNumber
            if (text.contains(operator)) {
                val indexOp = text.indexOf(operator)
                if (indexOp + 2 < text.length) {
                    val secondPart = text.substring(indexOp + 2)
                    secondNumber = parseDoubleSafe(secondPart)
                } else {
                    secondNumber = 0.0
                }
            }

            when (operator) {
                "+" -> result = firstNumber + secondNumber
                "-" -> result = firstNumber - secondNumber
                "*" -> result = firstNumber * secondNumber
                "/" -> {
                    // Evitar divisão por zero
                    if (secondNumber == 0.0) {
                        tvResult.text = "Erro"
                        return@setOnClickListener
                    } else {
                        result = firstNumber / secondNumber
                    }
                }
            }

            tvResult.text = result.toString()

            tvExpression.text = result.toString()

            isNewNumber = true
        }

        // ==== Botão DEL (apaga último caractere) ====
        findViewById<Button>(R.id.btnDel).setOnClickListener {
            var current = tvExpression.text.toString()
            if (current.isNotEmpty()) {
                current = current.substring(0, current.length - 1)
                tvExpression.text = current
            }
        }

        findViewById<Button>(R.id.btnClear).setOnClickListener {
            firstNumber = 0.0
            secondNumber = 0.0
            operator = ""
            isNewNumber = true
            tvExpression.text = "0"
            tvResult.text = ""
        }

        findViewById<Button>(R.id.btnSign).setOnClickListener {
            val current = tvExpression.text.toString()
            if (current.isEmpty()) {
                tvExpression.text = "-"
                isNewNumber = false
                return@setOnClickListener
            }
            if (current.startsWith("-")) {
                tvExpression.text = current.substring(1)
            } else {
                // Caso contrário, insere '-'
                tvExpression.text = "-$current"
            }
        }
    }

    private fun parseDoubleSafe(value: String): Double {
        return try {
            value.trim().toDouble()
        } catch (e: NumberFormatException) {
            0.0
        }
    }
}
