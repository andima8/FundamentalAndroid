package com.kotlin.andi.consumerapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kotlin.andi.consumerapp.R
import com.kotlin.andi.consumerapp.model.UserDBModel
import kotlinx.android.synthetic.main.user_list.view.*

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainHolder>() {

    private var uData: List<UserDBModel> = listOf()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(items: List<UserDBModel>) {
        this.uData = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MainHolder {
        val uView =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.user_list, viewGroup, false)
        return MainHolder(uView)
    }

    override fun getItemCount(): Int = uData.size

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(uData[position])
    }

    inner class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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