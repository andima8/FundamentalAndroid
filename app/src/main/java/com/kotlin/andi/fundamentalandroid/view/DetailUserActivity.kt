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
import com.kotlin.andi.fundamentalandroid.viewmodel.DetailViewModel
import com.kotlin.andi.fundamentalandroid.R
import com.kotlin.andi.fundamentalandroid.adapter.ViewPagerAdapter
import com.kotlin.andi.fundamentalandroid.invisible
import com.kotlin.andi.fundamentalandroid.model.User
import com.kotlin.andi.fundamentalandroid.visible
import kotlinx.android.synthetic.main.activity_detail_user.*


class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var detailViewModel: DetailViewModel
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
        showLoading(true)
        getData()
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

    private fun getData() {
        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)
        detailViewModel.setDetailUsers(username)
        val userLocation = resources.getString(R.string.location_unknown)
        detailViewModel.getDetailUsers().observe(this, Observer { userDetail ->
            userDetail?.apply {
                if (name != "null") tv_name.text = name.toString()
                else tv_name.text = "-"
                if(company != "null") tv_company.text = company.toString()
                else tv_company.text = "-"
                if(repository != "null") tv_repo.text = repository.toString()
                else tv_repo.text = "-"
                if(gists != "null") tv_gists.text = gists.toString()
                else tv_gists.text = "-"
                if(follower != "null") tv_follower.text = follower.toString()
                else tv_follower.text = "-"
                if(following != "null") tv_following.text = following.toString()
                else tv_following.text = "-"
                if(location != "null") tv_location.text = location.toString()
                else tv_location.text = userLocation
            }
            showLoading(false)
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            pg_detail.visible()
            label_name.invisible()
            tv_name.invisible()
            label_company.invisible()
            tv_company.invisible()
            label_repo.invisible()
            tv_repo.invisible()
            label_gists.invisible()
            tv_gists.invisible()
            label_follower.invisible()
            tv_follower.invisible()
            label_following.invisible()
            tv_following.invisible()
            tv_location.invisible()
        } else {
            pg_detail.invisible()
            label_name.visible()
            tv_name.visible()
            label_company.visible()
            tv_company.visible()
            label_repo.visible()
            tv_repo.visible()
            label_gists.visible()
            tv_gists.visible()
            label_follower.visible()
            tv_follower.visible()
            label_following.visible()
            tv_following.visible()
            tv_location.visible()
        }
    }

}