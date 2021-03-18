package com.social.socialcommunication.screen.in_call

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.social.socialcommunication.R
import com.social.socialcommunication.base.activity.ActivityPresenter
import com.social.socialcommunication.base.activity.ActivityViewOps
import com.social.socialcommunication.base.activity.BaseActivity
import com.social.socialcommunication.common.CommonUtils
import com.social.socialcommunication.common.Constant
import com.social.socialcommunication.common.ImageUtils
import com.social.socialcommunication.common.SharedPrefUtils
import com.social.socialcommunication.model.CloneMessenger
import com.social.socialcommunication.model.User
import com.social.socialcommunication.screen.list_chat.ListChatFragment
import com.social.socialcommunication.screen.main.MainActivity
import com.stringee.call.StringeeCall
import com.stringee.common.StringeeConstant
import kotlinx.android.synthetic.main.activity_ingoing_call.*
import kotlinx.android.synthetic.main.activity_ingoing_call.btnBack
import kotlinx.android.synthetic.main.activity_ingoing_call.btnOutCall
import kotlinx.android.synthetic.main.activity_ingoing_call.image_avatar_receiver
import kotlinx.android.synthetic.main.activity_ingoing_call.image_avatar_sender
import kotlinx.android.synthetic.main.activity_ingoing_call.tvNameReceiver
import kotlinx.android.synthetic.main.activity_ingoing_call.tvStatusCallOut
import kotlinx.android.synthetic.main.activity_ingoing_call.view_local
import kotlinx.android.synthetic.main.activity_ingoing_call.view_remove
import kotlinx.android.synthetic.main.activity_outgoing_call.*
import org.json.JSONObject

class InGoingCallActivity : BaseActivity<InGoingCallViewOps.PresenterViewOps>(),
    InGoingCallViewOps.ViewOps, View.OnClickListener {
    private var stringeeCall: StringeeCall? = null
    private var isVideocall = false
    private var userSender: User? = null
    private var userReceiver: User? = null

    companion object {
        fun newInstance(context: Context, userSender: User, isVideocall: Boolean): Intent {
            val intent = Intent(context, InGoingCallActivity::class.java)
            intent.putExtra(Constant.CALL_FROM, userSender)
            intent.putExtra(Constant.IS_VIDEOCALL, isVideocall)
            return intent
        }
    }

    private fun setDataOnview() {
        if (intent != null) {
            val userSender = intent.getSerializableExtra(Constant.CALL_FROM) as User
            ImageUtils.loadImage(this, image_avatar_sender, Uri.parse(userSender.avatar))
            ImageUtils.loadImage(this, image_avatar_receiver, Uri.parse(userSender.avatar))
            tvNameReceiver.text = userSender.nameUser
        }
    }

    private fun initAnswer() {
        if (intent != null) {
            userReceiver = SharedPrefUtils.getInstance(this).getAccount()
            userSender = intent?.getSerializableExtra(Constant.CALL_FROM) as User
            isVideocall = intent?.getBooleanExtra(Constant.IS_VIDEOCALL, false)!!
            val idSender = userSender?.idUser
            stringeeCall = ListChatFragment.callMap[idSender]
            stringeeCall?.enableVideo(isVideocall)
            if (isVideocall) {
                stringeeCall?.setQuality(StringeeConstant.QUALITY_FULLHD)
            }
            stringeeCall?.setCallListener(object : StringeeCall.StringeeCallListener {
                override fun onHandledOnAnotherDevice(
                    p0: StringeeCall?,
                    p1: StringeeCall.SignalingState?,
                    p2: String?
                ) {
                }

                override fun onCallInfo(p0: StringeeCall?, p1: JSONObject?) {
                }

                override fun onMediaStateChange(p0: StringeeCall?, p1: StringeeCall.MediaState?) {
                }

                override fun onRemoteStream(p0: StringeeCall?) {
                    runOnUiThread {
                        kotlin.run {
                            view_remove.addView(p0?.remoteView)
                            stringeeCall?.renderRemoteView(false)
                        }
                    }
                }

                override fun onSignalingStateChange(
                    p0: StringeeCall?,
                    p1: StringeeCall.SignalingState?,
                    p2: String?,
                    p3: Int,
                    p4: String?
                ) {
                    runOnUiThread {
                        kotlin.run {
                            when (p1) {
                                StringeeCall.SignalingState.CALLING -> {
                                    tvStatusCallOut.text = "Đang gọi..."
                                }
                                StringeeCall.SignalingState.RINGING -> {
                                    tvStatusCallOut.text = "Đang gọi..."
                                }
                                StringeeCall.SignalingState.ANSWERED -> {
                                    if (isVideocall) {
                                        view_remove.visibility = View.VISIBLE
                                        view_local.visibility = View.VISIBLE
                                    } else {
                                        btnInCall.visibility = View.GONE
                                        tvStatusCallOut.text = "Đang trong cuộc gọi"
                                    }
                                }
                                StringeeCall.SignalingState.ENDED, StringeeCall.SignalingState.BUSY -> {
                                    if (view_remove.visibility == View.VISIBLE || view_local.visibility == View.VISIBLE) {
                                        view_remove.visibility = View.GONE
                                        view_local.visibility = View.GONE
                                    }
                                    tvStatusCallOut.text = "Kết thúc"
//                                    sendMess()
                                    finish()
                                }
                            }
                        }
                    }
                }

                override fun onLocalStream(p0: StringeeCall?) {
                    runOnUiThread {
                        kotlin.run {
                            view_local.addView(p0?.localView)
                            stringeeCall?.renderLocalView(true)
                        }
                    }
                }

                override fun onError(p0: StringeeCall?, p1: Int, p2: String?) {
                }

            })

        }
        stringeeCall?.initAnswer(this, MainActivity.stringeeClient)
    }

    private fun sendMess() {
        val cloneMessenger = CloneMessenger()
        cloneMessenger.text =
            "Cuộc gọi kết thúc lúc " + CommonUtils.getCurrentDateString()
        cloneMessenger.createAt = CommonUtils.getCurrentDateString()
        cloneMessenger.userReceiver = userReceiver
        cloneMessenger.userSender = userSender
        cloneMessenger.id = userReceiver?.id + System.currentTimeMillis()
        mPresenter?.sendMessages(cloneMessenger)
    }

    private fun setEventClick() {
        btnInCall.setOnClickListener(this)
        btnOutCall.setOnClickListener(this)
        btnBack.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnOutCall, R.id.btnBack -> {
                if (stringeeCall != null) {
                    stringeeCall?.hangup()
                }
                finish()
            }
            R.id.btnInCall -> {
                if (stringeeCall != null) {
                    stringeeCall?.answer()
                }
            }
        }

    }

    override fun getViewResoure(): Int {
        return R.layout.activity_ingoing_call
    }

    override fun setUp() {
        setEventClick()
        setDataOnview()
        initAnswer()
    }

    override fun onRegisterPresenter(): Class<out ActivityPresenter<ActivityViewOps>> {
        return InGoingCallPresenter::class.java as Class<out ActivityPresenter<ActivityViewOps>>
    }
}