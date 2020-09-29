package com.kotlin.andi.consumerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.BounceInterpolator
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kotlin.andi.consumerapp.DatabaseContract.CONTENT_URI
import com.kotlin.andi.consumerapp.model.UserDBModel
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MainAdapter
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = MainAdapter()
        rv_favorite.layoutManager = LinearLayoutManager(this)
        rv_favorite.setHasFixedSize(true)
        getDataFromContentProvider()
        rv_favorite.adapter = ScaleInAnimationAdapter(adapter).apply {
            setDuration(1000)
            setInterpolator(BounceInterpolator())
            setFirstOnly(false)
        }
        adapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback {
            override fun onItemClicked(user: UserDBModel) {
                Snackbar.make(rv_favorite, "Hello ${user.username}", Snackbar.LENGTH_SHORT).show()
            }

        })

    }

    private fun getDataFromContentProvider() {
        userViewModel = ViewModelProvider(
            viewModelStore,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(UserViewModel::class.java)
        userViewModel.userLists.observe(this, Observer { users ->
            if (!users.isNullOrEmpty()) {
                adapter.setData(users)
            } else {
                Snackbar.make(rv_favorite, "Data Not Found", Snackbar.LENGTH_SHORT).show()
            }
        })
    }


}
