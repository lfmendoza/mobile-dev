package com.example.laboratorio_6

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.ComponentActivity
import org.json.JSONArray

data class City(val href: String, val name: String)

class MainActivity : ComponentActivity() {
    private val cityService = CityService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val citySpinner: Spinner = findViewById(R.id.citySpinner)

        cityService.getCities(object : CityService.CityCallback {
            override fun onSuccess(response: String) {
                val cityNames = parseCityNames(response)

                val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, cityNames)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                citySpinner.adapter = adapter
            }

            override fun onFailure(error: String) {
                Log.e("CityService", "Failed to get cities: $error")
            }
        })
    }

    private fun parseCityNames(response: String): List<String> {
        val cityNames = mutableListOf<String>()

        val jsonArray = JSONArray(response)
        for (i in 0 until jsonArray.length()) {
            val cityObject = jsonArray.getJSONObject(i)
            val cityName = cityObject.getString("name")
            cityNames.add(cityName)
        }

        return cityNames
    }
}
