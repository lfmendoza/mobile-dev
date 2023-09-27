import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.laboratorio4.R

class WelcomeActivity : AppCompatActivity() {

    private lateinit var welcomeTextView: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        welcomeTextView = findViewById(R.id.welcomeTextView)

        val username = intent.getStringExtra("username")
        val name = intent.getStringExtra("name")

        welcomeTextView.text = "Welcome, $name ($username)"
    }
}