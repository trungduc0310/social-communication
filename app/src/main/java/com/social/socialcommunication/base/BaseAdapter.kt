package com.social.socialcommunication.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseAdapter<T>.ViewHolder>() {

    private var listModel: ArrayList<T> = ArrayList()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            setInitViewHolder(itemView)
        }

        fun onBind(position: Int) {
            setOnBind(position)
        }
    }

    override fun getItemCount(): Int {
        return listModel.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(getItemViewResoure(), parent, false)
        return ViewHolder(view)
    }

    abstract fun getItemViewResoure(): Int
    abstract fun setOnBind(position: Int)
    abstract fun setInitViewHolder(itemView: View)
}