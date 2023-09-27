import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.laboratorio4.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class WelcomeActivity : AppCompatActivity() {

    private lateinit var welcomeTextView: TextView
    private lateinit var tvMensaje: TextView
    private lateinit var btnDate: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        welcomeTextView = findViewById(R.id.welcomeTextView)

        tvMensaje = findViewById(R.id.tvMensaje)
        btnDate = findViewById(R.id.getDate)

        val username = intent.getStringExtra("username")
        val name = intent.getStringExtra("name")

        val sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
        val jwt = sharedPreferences.getString("jwt", null)

        welcomeTextView.text = "Welcome, $name ($username)"

        btnDate.setOnClickListener {

            // Llamar a la función loginAndStoreJwt en un contexto de GlobalScope para realizar la operación de forma asincrónica
            GlobalScope.launch(Dispatchers.Main) {
                val unixtime = jwt?.let { it1 -> getUnixTimeFromService(applicationContext, it1) }
                if (unixtime != null) {
                    tvMensaje.text = "" + unixtime
                } else {
                    val toast = Toast.makeText(applicationContext, "Credenciales invalidas", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }

        }
    }
}

suspend fun getUnixTimeFromService(context: Context, jwt: String): String? {
    val json = JSONObject()

    val mediaType = "application/x-www-form-urlencoded; charset=UTF-8".toMediaType()
    val requestBody = json.toString().toRequestBody(mediaType)

    val request = Request.Builder()
        .url("http://10.0.2.2/php-prueba/public/resource.php")
        .get()
        .addHeader("Authorization", "Bearer $jwt")
        .build()


    return withContext(Dispatchers.IO) {
        val response = OkHttpClient().newCall(request).execute()
        val responseBody = response.body?.string()

        if (response.isSuccessful && responseBody != null && !responseBody.equals("error_bad_credentials")) {
            //val jwt = JSONObject(responseBody).getString("jwt")
            val jwt = responseBody
            println(jwt)
            return@withContext jwt
        } else {
            // Manejar error de autenticación u otra situación
            return@withContext null
        }
    }
}