package com.social.socialcommunication.screen.chat

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.social.socialcommunication.R
import com.social.socialcommunication.base.fragment.BaseFragment
import com.social.socialcommunication.base.fragment.FragmentPresenter
import com.social.socialcommunication.base.fragment.FragmentViewOps
import com.social.socialcommunication.common.ImageUtils
import com.social.socialcommunication.model.Messenger
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.messages.MessagesListAdapter
import kotlinx.android.synthetic.main.fragment_chat_messenger.*
import kotlinx.android.synthetic.main.layout_bottom_chat.*
import kotlinx.android.synthetic.main.layout_header_chat.*

class ChatFragment : BaseFragment<ChatViewOps.PresenterViewOps>(), ChatViewOps.ViewOps,
    MessagesListAdapter.SelectionListener, MessagesListAdapter.OnLoadMoreListener,
    View.OnClickListener {
    private lateinit var imageLoader: ImageLoader
    private val senderId = "0"
    private lateinit var messagesAdapter: MessagesListAdapter<Messenger>
    override fun getViewResoure(): Int {
        return R.layout.fragment_chat_messenger
    }

    override fun setUp(view: View) {
        imageLoader = ImageLoader { imageView, url, payload ->
            ImageUtils.loadImage(
                context!!,
                imageView,
                url!!
            )
        }
        setMessagesAdapter()
        setEventClick()
//        messagesAdapter.addToStart(Messenger(), true) //THêm tin nhắn
    }

    private fun setEventClick() {
        btnSendMessenger.setOnClickListener(this)
        btnTakeImage.setOnClickListener(this)
        btnTakeCamera.setOnClickListener(this)
        btnBack.setOnClickListener(this)
        btnCall.setOnClickListener(this)
        btnVideoCall.setOnClickListener(this)
        btnInfoChat.setOnClickListener(this)
    }

    private fun setMessagesAdapter() {
        messagesAdapter = MessagesListAdapter(senderId, imageLoader)
        messagesAdapter.setLoadMoreListener(this)
        messagesAdapter.enableSelectionMode(this)
        messagesAdapter.registerViewClickListener(
            R.id.messageUserAvatar
        ) { view, message ->
            //TODO avatar click
        }
        listMessage.setAdapter(messagesAdapter)
    }

    override fun onRegisterPresenter(): Class<out FragmentPresenter<FragmentViewOps>> {
        return ChatPresenter::class.java as Class<out FragmentPresenter<FragmentViewOps>>
    }

    override fun onLoadMore(page: Int, totalItemsCount: Int) {
        //TODO loadMore messenger
    }

    override fun onSelectionChanged(count: Int) {
        //TODO long click item mess
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnSendMessenger -> {
                //TODO SendClick
            }
            R.id.btnTakeImage -> {
                //TODO TakeImageFragment
            }
            R.id.btnTakeCamera -> {
                //TODO TakeCamera
            }
            R.id.btnBack -> {
                getActivityReference()?.onBackPressed()
            }
            R.id.btnCall -> {
                //TODO
            }
            R.id.btnVideoCall -> {
                //TODO
            }
            R.id.btnInfoChat -> {
                //TODO
            }
        }
    }
}