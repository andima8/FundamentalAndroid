package com.kotlin.andi.fundamentalandroid.view.fragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.andi.fundamentalandroid.R
import com.kotlin.andi.fundamentalandroid.adapter.UserAdapter
import com.kotlin.andi.fundamentalandroid.invisible
import com.kotlin.andi.fundamentalandroid.model.User
import com.kotlin.andi.fundamentalandroid.view.DetailUserActivity
import com.kotlin.andi.fundamentalandroid.viewmodel.MainViewModel
import com.kotlin.andi.fundamentalandroid.visible
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private lateinit var searchView: SearchView
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: UserAdapter
    private var listUser: ArrayList<User> = ArrayList()
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        searchView = rootView.findViewById(R.id.sv_main)
        recyclerView = rootView.findViewById(R.id.rv_user)


        adapter = UserAdapter(listUser)
        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        searchUser()
        searchAdapterViewConfig()
        configMainViewModel(adapter)

        return rootView
    }

    private fun configMainViewModel(adapter: UserAdapter) {
        mainViewModel.getUsers().observe(requireActivity(), Observer { userItems ->
            if (userItems != null) {
                adapter.setData(userItems)
                if (adapter.itemCount==0){
                    tv_notFound.visible()
                    iv_notFound.visible()
                } else {
                    tv_notFound.invisible()
                    iv_notFound.invisible()
                }
                recyclerView.visible()
                tv_searchHere.invisible()
                iv_search.invisible()
                showLoading(false)
            }
        })
    }

    private fun searchAdapterViewConfig() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)
        adapter.notifyDataSetChanged()
        recyclerView.adapter = ScaleInAnimationAdapter(adapter).apply {
            setDuration(1000)
            setInterpolator(BounceInterpolator())
            setFirstOnly(false)
        }
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: User) {
                val detailIntent = Intent(activity, DetailUserActivity::class.java)
                detailIntent.putExtra(DetailUserActivity.EXTRA_USER, user)
                startActivity(detailIntent)
            }

        })
    }

    private fun searchUser() {
        val searchHint = resources.getString(R.string.search_hint)
        searchView.queryHint = searchHint
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                recyclerView.invisible()
                showLoading(true)
                mainViewModel.setUsers(query)
                return true
            }
            override fun onQueryTextChange(query: String?): Boolean {
                return true
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            iv_search.invisible()
            tv_searchHere.invisible()
            tv_notFound.invisible()
            iv_notFound.invisible()
            sp_loading.visible()
            sp_loading.playAnimation()
        } else {
            sp_loading.invisible()
            sp_loading.pauseAnimation()
        }
    }


}