import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Redireciona para a MainActivity após o splash
        startActivity(Intent(this, MainActivity::class.java))
        finish() // Finaliza a SplashActivity para não voltar para ela
    }
}