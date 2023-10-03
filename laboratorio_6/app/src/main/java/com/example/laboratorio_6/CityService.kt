package com.example.laboratorio_6

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class CityService {
    interface CityCallback {
        fun onSuccess(response: String)
        fun onFailure(error: String)
    }

    private val client = OkHttpClient()

    fun getCities(callback: CityCallback) {
        val request = Request.Builder()
            .url("https://api.teleport.org/api/urban_areas/")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    callback.onSuccess(responseBody ?: "")
                } else {
                    callback.onFailure("Failed to fetch cities. Response code: ${response.code}")
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                callback.onFailure("Error: ${e.message}")
            }
        })
    }
}