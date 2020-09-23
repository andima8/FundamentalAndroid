package com.kotlin.andi.fundamentalandroid.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.andi.fundamentalandroid.R
import com.kotlin.andi.fundamentalandroid.adapter.FavoriteAdapter
import com.kotlin.andi.fundamentalandroid.db.UserDBModel
import com.kotlin.andi.fundamentalandroid.db.UserViewModel
import com.kotlin.andi.fundamentalandroid.view.DetailUserActivity

class FavoriteFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavoriteAdapter
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_favorite, container, false)
        recyclerView = rootView.findViewById(R.id.rv_favorite)

        adapter = FavoriteAdapter()
        userViewModel = ViewModelProvider(
            this
        ).get(UserViewModel::class.java)

        showUserFavorite()

        return rootView
    }

    private fun showUserFavorite() {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        userViewModel.readAllUser.observe(viewLifecycleOwner, Observer { items ->
            if (items != null) {
                adapter.setData(items)
            }
        })

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback{
            override fun onItemClicked(user: UserDBModel) {
                val detailIntent = Intent(activity, DetailUserActivity::class.java)
                detailIntent.putExtra(DetailUserActivity.EXTRA_DB, user)
                startActivity(detailIntent)
            }
        })

    }

}