package com.kotlin.andi.fundamentalandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kotlin.andi.fundamentalandroid.R
import com.kotlin.andi.fundamentalandroid.model.User
import kotlinx.android.synthetic.main.user_list.view.*


class UserAdapter(private val uData: ArrayList<User>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(items: ArrayList<User>) {
        uData.clear()
        uData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): UserViewHolder {
        val uView =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.user_list, viewGroup, false)
        return UserViewHolder(uView)
    }

    override fun getItemCount(): Int = uData.size


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(uData[position])
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(userItems: User) {
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
        fun onItemClicked(user: User)
    }
}
