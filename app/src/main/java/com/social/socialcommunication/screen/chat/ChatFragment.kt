package com.social.socialcommunication.screen.chat

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import com.social.socialcommunication.R
import com.social.socialcommunication.base.fragment.BaseFragment
import com.social.socialcommunication.base.fragment.FragmentPresenter
import com.social.socialcommunication.base.fragment.FragmentViewOps
import com.social.socialcommunication.common.CommonUtils
import com.social.socialcommunication.common.ImageUtils
import com.social.socialcommunication.common.SharedPrefUtils
import com.social.socialcommunication.model.CloneMessenger
import com.social.socialcommunication.model.Messenger
import com.social.socialcommunication.model.User
import com.social.socialcommunication.screen.out_call.OutGoingCallActivity
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.messages.MessagesListAdapter
import com.tuanfadbg.takephotoutils.TakePhotoCallback
import kotlinx.android.synthetic.main.fragment_chat_messenger.*
import kotlinx.android.synthetic.main.layout_bottom_chat.*
import kotlinx.android.synthetic.main.layout_header_chat.*
import java.io.File


class ChatFragment : BaseFragment<ChatViewOps.PresenterViewOps>, ChatViewOps.ViewOps,
    MessagesListAdapter.SelectionListener, MessagesListAdapter.OnLoadMoreListener,
    View.OnClickListener {
    companion object {
        fun newInstance(receiverUser: User): ChatFragment {
            return ChatFragment(receiverUser)
        }
    }


    private lateinit var imageLoader: ImageLoader
    private lateinit var messagesAdapter: MessagesListAdapter<Messenger>
    private lateinit var senderUser: User
    private var receiverUser: User
    private var isLoadHistory = true

    constructor(receiverUser: User) : super() {
        this.receiverUser = receiverUser
    }

    override fun getViewResoure(): Int {
        return R.layout.fragment_chat_messenger
    }

    override fun setUp() {
        setEventClick()
        imageLoader = ImageLoader { imageView, url, payload ->
            ImageUtils.loadImage(
                context!!,
                imageView,
                url!!
            )
        }
        senderUser = SharedPrefUtils.getInstance(getActivityContext()!!).getAccount()!!
        setDataOnView()
        setMessagesAdapter()
        mPresenter?.addMessagess(senderUser.id, receiverUser.id)
    }


    private fun setDataOnView() {
        ImageUtils.loadImage(context!!, imgUserAvatar, Uri.parse(receiverUser.avatar))
        tvUserName.text = receiverUser.nameUser
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
        messagesAdapter = MessagesListAdapter(senderUser.id, imageLoader)
        messagesAdapter.setLoadMoreListener(this)
        messagesAdapter.enableSelectionMode(this)
        messagesAdapter.registerViewClickListener(
            R.id.messageUserAvatar
        ) { view, message ->

        }
        listMessage.setAdapter(messagesAdapter)
    }

    override fun onRegisterPresenter(): Class<out FragmentPresenter<FragmentViewOps>> {
        return ChatPresenter::class.java as Class<out FragmentPresenter<FragmentViewOps>>
    }

    override fun onLoadMore(page: Int, totalItemsCount: Int) {
    }

    override fun onSelectionChanged(count: Int) {
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnSendMessenger -> {
                val contentText = edtInputMessenger.text.toString()
                if (contentText.isNotEmpty() && senderUser != null && receiverUser != null) {
                    val messenger = CloneMessenger()
                    messenger.userSender = senderUser
                    messenger.userReceiver = receiverUser
                    messenger.id = receiverUser.id + System.currentTimeMillis()
                    messenger.createAt = CommonUtils.getCurrentDateString()!!
                    messenger.text = contentText
                    mPresenter?.sendMessages(messenger)
                    edtInputMessenger.setText("")
                }

            }
            R.id.btnTakeImage -> {
                showToast("Tính năng sẽ được cập nhật ở phiên bản tiếp theo")
            }
            R.id.btnTakeCamera -> {
                showToast("Tính năng sẽ được cập nhật ở phiên bản tiếp theo")
            }
            R.id.btnBack -> {
                CommonUtils.hideSoftKeyboard(getActivityReference()!!)
                getActivityReference()?.onBackPressed()
            }
            R.id.btnCall -> {
                requirePermission()
                startActivity(
                    OutGoingCallActivity.newInstance(
                        getActivityContext()!!,
                        senderUser.idUser.toString(),
                        receiverUser.idUser.toString(),
                        receiverUser,
                        false
                    )
                )
            }
            R.id.btnVideoCall -> {
                requirePermission()
                startActivity(
                    OutGoingCallActivity.newInstance(
                        getActivityContext()!!,
                        senderUser.idUser.toString(),
                        receiverUser.idUser.toString(),
                        receiverUser,
                        true
                    )
                )
            }
            R.id.btnInfoChat -> {
                showToast("Tính năng sẽ được cập nhật ở phiên bản tiếp theo")
            }
        }
    }

    private fun requirePermission() {
        ActivityCompat.requestPermissions(
            getActivityReference()!!, arrayOf<String>(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            ), 1
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            if (grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {

            } else {
                showToast("Cần cấp quyền để tiếp tục cuộc gọi")
            }
            return
        }
    }

    override fun onSendMess(messenger: Messenger) {
        try {
            messagesAdapter.addToStart(messenger, true)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    override fun onSendedMess(messenger: Messenger) {
    }

    override fun onSendFail(mess: String) {
    }

    override fun onReadListchatSuccess(listChat: ArrayList<Messenger>) {
        messagesAdapter.addToEnd(listChat, true)
    }


}