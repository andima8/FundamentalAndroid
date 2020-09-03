package com.kotlin.andi.fundamentalandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity(), LoadingImplementation {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()

        sp_animation.playAnimation()
        LoadingView(this).execute()
    }

    override fun loading() {
        val startIntent = Intent(this@SplashScreen, MainActivity::class.java)
        startActivity(startIntent)
        finish()
    }
}