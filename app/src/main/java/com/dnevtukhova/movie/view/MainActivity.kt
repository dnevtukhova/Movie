package com.dnevtukhova.movie.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dnevtukhova.movie.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
    }
}