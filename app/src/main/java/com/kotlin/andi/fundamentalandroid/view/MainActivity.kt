package com.kotlin.andi.fundamentalandroid.view


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.kotlin.andi.fundamentalandroid.R
import com.kotlin.andi.fundamentalandroid.view.settings.SettingActivity
import com.kotlin.andi.fundamentalandroid.view.fragment.FavoriteFragment
import com.kotlin.andi.fundamentalandroid.view.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
        }

        bottomNavigation()

    }

    private fun loadFavoriteFragment() {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.main_container, FavoriteFragment(), FavoriteFragment::class.java.simpleName)
                .addToBackStack(null)
                .commit()
    }

    private fun loadHomeFragment() {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.main_container, HomeFragment(), HomeFragment::class.java.simpleName)
                .addToBackStack(null)
                .commit()

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //if (item.itemId == R.id.change_language) {
            val mIntent = Intent(this, SettingActivity::class.java)
                //Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)

        return super.onOptionsItemSelected(item)
    }

    private fun bottomNavigation() {
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    loadHomeFragment()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.favorite -> {
                    loadFavoriteFragment()
                    return@setOnNavigationItemSelectedListener true
                }
            }
            true
        }
        bottom_navigation.selectedItemId = R.id.home
    }

}
