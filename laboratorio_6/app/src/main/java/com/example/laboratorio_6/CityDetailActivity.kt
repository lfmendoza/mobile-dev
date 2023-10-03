package com.example.laboratorio_6

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
class CityDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_detail)

        val city: City = intent.getSerializableExtra("city") as City

        getCityInformation(city)
        getCityPhotos(city)
    }

    private fun getCityInformation(city: City) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val information = fetchCityInformation(city)
                // Handle the city information
                Log.d("CityDetailActivity", "City Information: $information")
            } catch (e: Exception) {
                // Handle the error
                Log.e("CityDetailActivity", "Error getting city information: ${e.message}")
            }
        }
    }

    private suspend fun fetchCityInformation(city: City): String {
        return withContext(Dispatchers.IO) {
            val url = "https://api.teleport.org/api/urban_areas/slug:zurich/"
            val response = OkHttpClient().newCall(Request.Builder().url(url).build()).execute()
            return response.body?.string() ?: ""
        }
    }

    private fun getCityPhotos(city: City) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val photos = fetchCityPhotos(city)
                // Handle the city photos
                Log.d("CityDetailActivity", "City Photos: $photos")
            } catch (e: Exception) {
                // Handle the error
                Log.e("CityDetailActivity", "Error getting city photos: ${e.message}")
            }
        }
    }

    private suspend fun fetchCityPhotos(city: City): List<String> {
        return withContext(Dispatchers.IO) {
            // Make API call to get city photos
            // Replace the URL with the actual endpoint for city photos
            val url = "city.photosEndpoint"
            // Use OkHttp or another HTTP client to make the request
            // Example using OkHttp (ensure it's added as a dependency)
            // val response = OkHttpClient().newCall(Request.Builder().url(url).build()).execute()
            // val jsonResponse = response.body?.string() ?: ""
            // Parse the JSON response to get a list of photo URLs
            // For simplicity, returning a placeholder list of URLs
            return@withContext listOf("photo1_url", "photo2_url", "photo3_url")
        }
    }
}