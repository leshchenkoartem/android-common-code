package com.app3null.commoncodelib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app3null.common_code.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.test()
    }
}
