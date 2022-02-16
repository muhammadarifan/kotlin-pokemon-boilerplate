package com.muhammadarifan.pokemonboilerplate.activity.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.muhammadarifan.pokemonboilerplate.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        supportActionBar?.hide()
    }
}