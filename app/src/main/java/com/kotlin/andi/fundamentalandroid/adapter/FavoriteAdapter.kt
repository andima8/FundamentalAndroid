package com.kotlin.andi.fundamentalandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kotlin.andi.fundamentalandroid.R
import com.kotlin.andi.fundamentalandroid.db.UserDBModel
import kotlinx.android.synthetic.main.user_list.view.*

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder>() {

    private var uData = emptyList<UserDBModel>()
    private var onItemClickCallback: FavoriteAdapter.OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: FavoriteAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(items: List<UserDBModel>) {
        this.uData = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FavoriteHolder {
        val uView =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.user_list, viewGroup, false)
        return FavoriteHolder(uView)
    }

    override fun getItemCount(): Int = uData.size

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        holder.bind(uData[position])
    }

    inner class FavoriteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(userItems: UserDBModel) {
            with(itemView) {
                profile_name.text = userItems.username
                Glide.with(itemView.context).load(userItems.avatar).into(profile_image)
                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(userItems)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: UserDBModel)
    }
}