package com.kotlin.andi.fundamentalandroid

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.andi.fundamentalandroid.adapter.UserAdapter
import com.kotlin.andi.fundamentalandroid.model.User
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    /* private var dataUser: MutableList<User> = mutableListOf()*/
    private lateinit var searchView: SearchView
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: UserAdapter
    private var dataList: ArrayList<User> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setLogo(R.drawable.github)
        supportActionBar?.setDisplayUseLogoEnabled(true)

        searchView = findViewById(R.id.sv_main)
        adapter = UserAdapter()
        /*rv_user.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            rv_user.setHasFixedSize(true)
            rv_user.adapter = ScaleInAnimationAdapter(
                UserAdapter(
                    this@MainActivity,
                    displayList
                ) {
                    val detailIntent = Intent(this@MainActivity, DetailUserActivity::class.java)
                    detailIntent.putExtra(DetailUserActivity.EXTRA_USER, it)
                    setupWindowAnimations()
                    startActivity(detailIntent)
                }).apply {
                setDuration(1000)
                setInterpolator(BounceInterpolator())
                setFirstOnly(false)
            }
        }*/


        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    if(query.isNotEmpty()){
                        dataList.clear()
                        viewConfig()
                        mainViewModel.setUsers(query)
                        showLoading(true)
                        configMainViewModel(adapter)
                    } else {
                        return true
                    }
                }

                return true
            }


            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }
        })

    }

    /*private fun setupWindowAnimations() {
        val slide = Slide()
        slide.duration = 1000
        window.exitTransition = slide
    }*/

    private fun viewConfig() {
        rv_user.layoutManager = LinearLayoutManager(this)
        rv_user.setHasFixedSize(true)
        adapter.notifyDataSetChanged()
        rv_user.adapter = adapter
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