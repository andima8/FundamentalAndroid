package com.kotlin.andi.fundamentalandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kotlin.andi.fundamentalandroid.R
import com.kotlin.andi.fundamentalandroid.model.User
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.user_list.*

class UserAdapter(
    private val context: Context,
    private val users: List<User>,
    private val listener: (User) -> Unit
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.user_list,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(users[position], listener)
    }



    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bindItem(users: User, listener: (User) -> Unit) {
            profile_name.text = users.name
            Glide.with(itemView.context).load(users.avatar).into(profile_image)

            itemView.setOnClickListener {
                listener(users)
            }
        }
    }
}
