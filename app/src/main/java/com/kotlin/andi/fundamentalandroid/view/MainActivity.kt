package com.kotlin.andi.fundamentalandroid.view

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.animation.BounceInterpolator
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.andi.fundamentalandroid.R
import com.kotlin.andi.fundamentalandroid.adapter.UserAdapter
import com.kotlin.andi.fundamentalandroid.invisible
import com.kotlin.andi.fundamentalandroid.model.User
import com.kotlin.andi.fundamentalandroid.viewmodel.MainViewModel
import com.kotlin.andi.fundamentalandroid.visible
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var searchView: SearchView
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: UserAdapter
    private var listUser: ArrayList<User> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            title = ""
            setLogo(R.drawable.github)
            setDisplayUseLogoEnabled(true)
        }
        searchView = findViewById(R.id.sv_main)
        adapter = UserAdapter(listUser)

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        searchUser()
        searchAdapterViewConfig()
        configMainViewModel(adapter)
    }

    private fun searchUser(){
        val searchHint = resources.getString(R.string.search_hint)
        searchView.queryHint = searchHint
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                showLoading(true)
                mainViewModel.setUsers(query)
                return true
            }
            override fun onQueryTextChange(query: String?): Boolean {
                return true
            }
        })
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

    private fun configMainViewModel(adapter: UserAdapter) {
        mainViewModel.getUsers().observe(this, Observer { userItems ->
            if (userItems != null) {
                adapter.setData(userItems)
                if (adapter.itemCount==0){
                    tv_notFound.visible()
                    iv_notFound.visible()
                } else {
                    tv_notFound.invisible()
                    iv_notFound.invisible()
                }
                showLoading(false)
            }
        })
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

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visible()
            iv_search.invisible()
            tv_searchHere.invisible()
        } else {
            progressBar.invisible()
        }
    }
}