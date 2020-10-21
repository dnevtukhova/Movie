package com.dnevtukhova.movie.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dnevtukhova.movie.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                FilmsListFragment(),
                FilmsListFragment.TAG
            )
            .commit()
    }

}