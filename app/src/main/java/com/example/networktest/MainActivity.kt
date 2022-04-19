package com.example.networktest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import java.util.*
import retrofit2.converter.gson.GsonConverterFactory


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timeStamp = System.currentTimeMillis();
        //As this is network call it should be done in a separate thread
        Thread {
            val data = RequestHandler.requestGET("https://www.vedantu.com/mobile/toggle.json")
            Log.d(TAG, "onCreate: $data")
            Log.d(TAG, "timeDiff: ${System.currentTimeMillis() - timeStamp}")
        }.start()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.vedantu.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val timeStamp2 = System.currentTimeMillis();
        val service: ApiService = retrofit.create(ApiService::class.java)
        service.getFlag().enqueue(object : Callback<Map<String, Any>?> {
            override fun onResponse(
                call: Call<Map<String, Any>?>?,
                response: Response<Map<String, Any>?>?
            ) {
                Log.d(TAG, "onResponse: ${response?.body()}")
                Log.d(TAG, "retrofit timeDiff: ${System.currentTimeMillis() - timeStamp2}")

            }

            override fun onFailure(call: Call<Map<String, Any>?>?, t: Throwable?) {

            }
        })
    }


    interface ApiService {
        @GET("mobile/toggle.json")
        fun getFlag(): Call<Map<String, Any>?>
    }
}
