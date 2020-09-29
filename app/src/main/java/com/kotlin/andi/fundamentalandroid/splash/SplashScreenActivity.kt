package com.kotlin.andi.fundamentalandroid.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kotlin.andi.fundamentalandroid.R
import com.kotlin.andi.fundamentalandroid.view.MainActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*

@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity(),
    LoadingImplementation {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()

        sp_animation.playAnimation()
        LoadingView(this).execute()
    }

    override fun loading() {
        val startIntent = Intent(this@SplashScreenActivity, MainActivity::class.java)
        startActivity(startIntent)
        finish()
    }
}