package com.social.socialcommunication.screen.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.social.socialcommunication.R
import kotlinx.android.synthetic.main.item_list_setting_account.view.*

class SettingProfileAdapter : RecyclerView.Adapter<SettingProfileAdapter.ViewHolder>() {
    private var listText = arrayListOf(
        R.string.text_edit_profile,
        R.string.text_support,
        R.string.text_policy,
        R.string.intro_app,
        R.string.share_app
    )
    private var listIcon = arrayListOf(
        R.drawable.ic_setting_account_white,
        R.drawable.ic_support_white,
        R.drawable.ic_policy_white,
        R.drawable.ic_info_application_white,
        R.drawable.ic_share_application_white
    )
    private lateinit var onItemSettingClickListener: OnItemSettingClickListener

    fun setItemSettingClickListener(onItemSettingClickListener: OnItemSettingClickListener) {
        this.onItemSettingClickListener = onItemSettingClickListener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                when (layoutPosition) {
                    0 -> {
                        onItemSettingClickListener.onEditAccount()
                    }
                    1 -> {
                        onItemSettingClickListener.onSupport()
                    }
                    2 -> {
                        onItemSettingClickListener.onPolicy()
                    }
                    3 -> {
                        onItemSettingClickListener.onIntroApp()
                    }
                    4 -> {
                        onItemSettingClickListener.onShareApp()
                    }
                }
            }
        }

        fun onBind(position: Int) {
            val contentOption = listText[position]
            val iconOption = listIcon[position]
            itemView.tvContentSetting.setText(contentOption)
            itemView.imgItemSetting.setImageResource(iconOption)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_setting_account, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listIcon.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)
    }

    interface OnItemSettingClickListener {
        fun onEditAccount()
        fun onSupport()
        fun onPolicy()
        fun onIntroApp()
        fun onShareApp()
    }
}