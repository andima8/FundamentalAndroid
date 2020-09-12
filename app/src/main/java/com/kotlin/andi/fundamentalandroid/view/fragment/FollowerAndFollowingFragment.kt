package com.kotlin.andi.fundamentalandroid.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.andi.fundamentalandroid.R
import com.kotlin.andi.fundamentalandroid.adapter.UserAdapter
import com.kotlin.andi.fundamentalandroid.model.User
import com.kotlin.andi.fundamentalandroid.viewmodel.FragmentViewModel
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter

class FollowerAndFollowingFragment : Fragment() {

    companion object {
        fun newInstance(): FollowerAndFollowingFragment {
            return FollowerAndFollowingFragment()
        }
    }

    private var position: Int = 0
    private lateinit var username: String
    private lateinit var fragViewModel: FragmentViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var progressBar: ProgressBar
    private  lateinit var recyclerView: RecyclerView
    private var listUser: ArrayList<User> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_follower_and_following, container, false)
        position = arguments?.getInt("position") ?: 0
        username = arguments?.getString("username") ?: ""
        progressBar = rootView.findViewById(R.id.frag_progressBar)
        recyclerView = rootView.findViewById(R.id.frag_recyclerView)
        adapter = UserAdapter(listUser)

        fragViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FragmentViewModel::class.java)
        adapterViewConfig()
        loadData()
        showLoading(true)
        configMainViewModel(adapter)
        return rootView
    }


    private fun configMainViewModel(adapter: UserAdapter) {
        activity?.let {
            fragViewModel.getUsers().observe(it, Observer { userItems ->
                if (userItems != null) {
                    adapter.setData(userItems)
                    showLoading(false)
                }
            })
        }
    }

    private fun loadData() {
        when (position) {
            0 -> {
                fragViewModel.setFollowers(username)
            }
            else -> {
                fragViewModel.setFollowing(username)
            }
        }
    }

    private fun adapterViewConfig() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)
        adapter.notifyDataSetChanged()
        recyclerView.adapter = ScaleInAnimationAdapter(adapter).apply {
            setDuration(1000)
            setInterpolator(BounceInterpolator())
            setFirstOnly(false)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }


}