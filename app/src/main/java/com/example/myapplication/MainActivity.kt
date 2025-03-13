package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    private lateinit var tvExpression: TextView
    private lateinit var tvResult: TextView

    private var firstNumber = 0.0
    private var secondNumber = 0.0
    private var operator = ""

    private var isNewNumber = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Configuração do DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout)

        // Configuração do NavigationView
        val navigationView: NavigationView = findViewById(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            // Fechar o Drawer quando um item for clicado
            drawerLayout.closeDrawers()
            true
        }

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
                tvExpression.text = "-$current"
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_drawer -> {
                if (drawerLayout.isDrawerOpen(findViewById(R.id.navigation_view))) {
                    drawerLayout.closeDrawers()
                } else {
                    drawerLayout.openDrawer(findViewById(R.id.navigation_view))
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
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
