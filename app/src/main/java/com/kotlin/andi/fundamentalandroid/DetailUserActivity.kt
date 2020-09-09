package com.kotlin.andi.fundamentalandroid

import android.os.Bundle
import android.transition.Fade
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
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

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = data?.name
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        tv_name.text = data?.name
        tv_username.text = getString(R.string.username_logo, data?.username)
        showFollower.text = data?.follower.toString()
        showFollowing.text = data?.following.toString()
        showCompany.text = data?.company
        showRepo.text = data?.repository.toString()
        showLocation.text = data?.location
        Glide.with(this).load(data?.avatar).into(profile_user)

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

}