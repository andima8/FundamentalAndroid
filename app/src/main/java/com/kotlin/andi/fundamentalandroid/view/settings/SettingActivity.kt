package com.kotlin.andi.fundamentalandroid.view.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kotlin.andi.fundamentalandroid.R

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.settings)

        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.setting_holder,
                MySettingsFragment()
            )
            .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}