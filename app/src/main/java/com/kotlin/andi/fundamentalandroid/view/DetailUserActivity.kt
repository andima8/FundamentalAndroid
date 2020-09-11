package com.kotlin.andi.fundamentalandroid.view

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.transition.Fade
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.kotlin.andi.fundamentalandroid.R
import com.kotlin.andi.fundamentalandroid.adapter.ViewPagerAdapter
import com.kotlin.andi.fundamentalandroid.invisible
import com.kotlin.andi.fundamentalandroid.model.User
import com.kotlin.andi.fundamentalandroid.viewmodel.MainViewModel
import com.kotlin.andi.fundamentalandroid.visible
import kotlinx.android.synthetic.main.activity_detail_user.*
import kotlinx.android.synthetic.main.activity_main.*


class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)
        setupWindowAnimations()

        val data = intent.getParcelableExtra<User>(EXTRA_USER)

        tv_username.text = data?.username.toString()
        username = data?.username.toString()
        Glide.with(this).load(data?.avatar).into(profile_user)

        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            title = username
            setDisplayShowTitleEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        data?.let { getData(it) }

        viewPager()
    }

    private fun viewPager() {
        viewpager_detail.adapter =
            ViewPagerAdapter(
                this,
                supportFragmentManager,
                username
            )
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

    private fun getData(userData: User) {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
        mainViewModel.setDetailUsers(userData.username)
        mainViewModel.getDetailUsers().observe(this, Observer { userDetail ->
            if (userDetail != null) {
                tv_name.text = userDetail.name.toString()
                tv_company.text = userDetail.company.toString()
                tv_follower.text = userDetail.follower.toString()
                tv_following.text = userDetail.following.toString()
                tv_location.text = userDetail.location.toString()
            }
        })
    }

}