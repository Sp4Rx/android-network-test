package com.example.networktest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


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
    }
}
