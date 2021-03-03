package com.social.socialcommunication.screen.list_contact

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.social.socialcommunication.R
import com.social.socialcommunication.base.OnItemClickListener
import com.social.socialcommunication.common.ImageUtils
import com.social.socialcommunication.model.PhoneBook
import kotlinx.android.synthetic.main.item_contact_main_layout.view.*

class ListContactAdapter : RecyclerView.Adapter<ListContactAdapter.ViewHolder>() {
    private var listContact: ArrayList<PhoneBook> = ArrayList()
    private var itemClickListener: OnItemClickListener<PhoneBook>? = null

    fun setListContact(listContact: ArrayList<PhoneBook>) {
        this.listContact = listContact
        notifyDataSetChanged()
    }

    fun setItemClickListener(itemClickListener: OnItemClickListener<PhoneBook>) {
        this.itemClickListener = itemClickListener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                itemClickListener?.itemClickListener(layoutPosition, listContact[layoutPosition])
            }
        }

        fun onBind(position: Int) {
            val phoneBook = listContact[position]
            ImageUtils.loadImageContact(itemView.context, itemView.imgUserAvatar, phoneBook.photo)
            itemView.tvUserName.text = phoneBook.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact_main_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listContact.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)
    }
}