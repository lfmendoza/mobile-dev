import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import com.example.laboratorio4.R

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)

        requestQueue = Volley.newRequestQueue(this)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            login(username, password)
        }
    }

    private fun login(username: String, password: String) {
        val url = "http://10.0.2.2/php-prueba/public/authenticate.php"
        val params = HashMap<String, String>()
        params["username"] = username
        params["password"] = password

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            url,
            JSONObject(params as Map<*, *>?),
            Response.Listener { response ->
                try {
                    val success = response.getBoolean("success")
                    if (success) {
                        val jwt = response.getString("jwt")

                        val sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("jwt", jwt)
                        editor.apply()

                        val intent = Intent(this, WelcomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Inicio de sesión fallido", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                error.printStackTrace()
                Toast.makeText(this, "Error de red", Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(jsonObjectRequest)
    }
}