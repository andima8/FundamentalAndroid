package com.kotlin.andi.fundamentalandroid.view

import android.os.Bundle
import android.view.View
import android.view.animation.BounceInterpolator
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.andi.fundamentalandroid.R
import com.kotlin.andi.fundamentalandroid.adapter.UserAdapter
import com.kotlin.andi.fundamentalandroid.viewmodel.MainViewModel
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    private lateinit var searchView: SearchView
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = ""
        supportActionBar?.setLogo(R.drawable.github)
        supportActionBar?.setDisplayUseLogoEnabled(true)

        searchView = findViewById(R.id.sv_main)
        adapter = UserAdapter()

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        searchView.queryHint = "Search Username"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    if(query.isNotEmpty()){
                        searchAdapterViewConfig()
                        mainViewModel.setUsers(query)
                        showLoading(true)
                        configMainViewModel(adapter)
                    } else {
                        return true
                    }
                }
                return false
            }
            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }
        })
        searchAdapterViewConfig()
        configMainViewModel(adapter)
    }


    private fun searchAdapterViewConfig() {
        rv_user.layoutManager = LinearLayoutManager(this)
        rv_user.setHasFixedSize(true)
        adapter.notifyDataSetChanged()
        rv_user.adapter = ScaleInAnimationAdapter(adapter).apply {
            setDuration(1000)
            setInterpolator(BounceInterpolator())
            setFirstOnly(false)
        }

    }

    private fun configMainViewModel(adapter: UserAdapter){
        mainViewModel.getUsers().observe(this, Observer { userItems ->
            if (userItems != null) {
                adapter.setData(userItems)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

}