package com.kotlin.andi.fundamentalandroid.view

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.transition.Fade
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.kotlin.andi.fundamentalandroid.R
import com.kotlin.andi.fundamentalandroid.adapter.ViewPagerAdapter
import com.kotlin.andi.fundamentalandroid.model.User
import kotlinx.android.synthetic.main.activity_detail_user.*


class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)
        setupWindowAnimations()

        val data = intent.getParcelableExtra<User>(EXTRA_USER)

        tv_name.text = data?.username.toString()
        val username = data?.username
        Glide.with(this).load(data?.avatar).into(profile_user)

        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            title = username
            setDisplayShowTitleEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        viewpager_detail.adapter =
            username?.let {
                ViewPagerAdapter(
                    this,
                    supportFragmentManager,
                    it
                )
            }
        tabs_detail.setupWithViewPager(viewpager_detail)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        supportFinishAfterTransition()
        return true
    }

    private fun setupWindowAnimations() {
        val fade = Fade()
        fade.duration = 1000
        window.enterTransition = fade
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.change_language) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }

}