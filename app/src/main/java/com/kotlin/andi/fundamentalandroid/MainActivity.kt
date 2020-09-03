package com.kotlin.andi.fundamentalandroid

import android.content.Intent
import android.os.Bundle
import android.transition.Slide
import android.view.Menu
import android.view.animation.BounceInterpolator
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.andi.fundamentalandroid.adapter.UserAdapter
import com.kotlin.andi.fundamentalandroid.model.User
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private var dataUser: MutableList<User> = mutableListOf()
    val displayList: MutableList<User> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setLogo(R.drawable.github)
        supportActionBar?.setDisplayUseLogoEnabled(true)
        getData()

        rv_user.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            rv_user.setHasFixedSize(true)
            rv_user.adapter = ScaleInAnimationAdapter(UserAdapter(this@MainActivity, displayList){
                val detailIntent = Intent(this@MainActivity, DetailUserActivity::class.java)
                detailIntent.putExtra(DetailUserActivity.EXTRA_USER, it)
                setupWindowAnimations()
                startActivity(detailIntent)
            }).apply {
                setDuration(1000)
                setInterpolator(BounceInterpolator())
                setFirstOnly(false)
            }
        }

    }

    private fun setupWindowAnimations() {
        val slide = Slide()
        slide.duration = 1000
        window.exitTransition = slide
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val search = menu?.findItem(R.id.appSearchBar)
        val searchView = search?.actionView as SearchView
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query!!.isNotEmpty()) {
                    displayList.clear()
                    val scrQuery = query.toLowerCase(Locale.ROOT)
                    dataUser.forEach{
                        if(it.name.toLowerCase(Locale.ROOT).contains(scrQuery)) {
                            displayList.add(it)
                        }
                    }
                    rv_user.adapter!!.notifyDataSetChanged()
                } else {
                    displayList.clear()
                    displayList.addAll(dataUser)
                    rv_user.adapter!!.notifyDataSetChanged()
                }
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun getData() {
        val avatar = resources.obtainTypedArray(R.array.avatar)
        val company = resources.getStringArray(R.array.company)
        val follower = resources.getStringArray(R.array.followers)
        val following = resources.getStringArray(R.array.following)
        val location = resources.getStringArray(R.array.location)
        val name = resources.getStringArray(R.array.name)
        val repository = resources.getStringArray(R.array.repository)
        val username = resources.getStringArray(R.array.username)
        dataUser.clear()
        for (i in name.indices) {
            dataUser.add(
                User(
                    avatar.getResourceId(i, 0),
                    company[i],
                    follower[i],
                    following[i],
                    location[i],
                    name[i],
                    repository[i],
                    username[i]
                )
            )
        }
        displayList.addAll(dataUser)

        avatar.recycle()
    }


}