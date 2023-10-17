package com.example.laboratorio_5;

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import okhttp3.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getPokemonNames()
        // getPokeathlonSpeedStats()
    }

    private fun getPokemonNames() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val pokemonNames = fetchPokemonNames()
                Log.d("MainActivity", "Pokemon Names: $pokemonNames")
            } catch (e: Exception) {

                Log.e("MainActivity", "Error getting Pokemon names: ${e.message}")
            }
        }
    }

    private suspend fun fetchPokemonNames(): List<String> {
        return withContext(Dispatchers.IO) {
            val url = "https://pokeapi.co/api/v2/pokemon/"
            val response = OkHttpClient().newCall(Request.Builder().url(url).build()).execute()
            val jsonResponse = response.body?.string() ?: ""

            val names = mutableListOf<String>()
            val jsonArray = JSONArray(jsonResponse)
            for (i in 0 until jsonArray.length()) {
                val pokemonObject = jsonArray.getJSONObject(i)
                val pokemonName = pokemonObject.getString("name")
                names.add(pokemonName)
            }

            return@withContext names
        }
    }

    private fun getPokeathlonSpeedStats() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val speedStats = fetchPokeathlonSpeedStats()
                Log.d("MainActivity", "Speed Stats: $speedStats")
            } catch (e: Exception) {
                Log.e("MainActivity", "Error getting speed stats: ${e.message}")
            }
        }
    }

    private suspend fun fetchPokeathlonSpeedStats(): List<String> {
        return withContext(Dispatchers.IO) {
            val url = "https://pokeapi.co/api/v2/pokeathlon-stat/speed"
            val response = OkHttpClient().newCall(Request.Builder().url(url).build()).execute()
            val jsonResponse = response.body?.string() ?: ""

            val stats = mutableListOf<String>()
            val jsonArray = JSONArray(jsonResponse)
            for (i in 0 until jsonArray.length()) {
                val statObject = jsonArray.getJSONObject(i)
                val statName = statObject.getString("name")
                stats.add(statName)
            }

            return@withContext stats
        }
    }
}