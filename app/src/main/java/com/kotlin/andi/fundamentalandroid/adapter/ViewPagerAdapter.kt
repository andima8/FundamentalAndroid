package com.kotlin.andi.fundamentalandroid.adapter

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.kotlin.andi.fundamentalandroid.R
import com.kotlin.andi.fundamentalandroid.view.fragment.FollowerAndFollowingFragment


class ViewPagerAdapter(private val mContext: Context, fm: FragmentManager, private val username: String) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        val b = Bundle()
        b.putInt("position", position)
        b.putString("username", username)
        val frag = FollowerAndFollowingFragment.newInstance()
        frag.arguments = b
        return frag
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? {
        val follower = mContext.resources.getString(R.string.follower)
        val following = mContext.resources.getString(R.string.following)
        return when (position) {
            0 -> follower
            else -> following
        }
    }

}