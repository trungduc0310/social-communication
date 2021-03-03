package com.social.socialcommunication.screen.list_chat

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.social.socialcommunication.R
import com.social.socialcommunication.base.OnItemClickListener
import com.social.socialcommunication.common.ImageUtils
import com.social.socialcommunication.model.User
import kotlinx.android.synthetic.main.item_chat_main_layout.view.*

class ListChatAdapter : RecyclerView.Adapter<ListChatAdapter.ViewHolder>() {
    private var listUser: ArrayList<User> = ArrayList()
    private lateinit var itemClickListener: OnItemClickListener<User>

    fun setItemClickListener(itemClickListener: OnItemClickListener<User>) {
        this.itemClickListener = itemClickListener
    }

    fun setListUser(listUser: ArrayList<User>) {
        this.listUser = listUser
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                itemClickListener.itemClickListener(layoutPosition, listUser[layoutPosition])
            }
        }

        fun onBind(position: Int) {
            val user = listUser[position]
            ImageUtils.loadImage(itemView.context, itemView.imgUserAvatar, Uri.parse(user.avatar!!))
            itemView.tvUserName.text = user.nameUser
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_main_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)
    }
}