package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicia a MainActivity após a Splash Screen
        startActivity(Intent(this, MainActivity::class.java))
        finish() // Fecha a SplashActivity para que o usuário não possa voltar para ela
    }
}
