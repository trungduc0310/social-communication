package com.social.socialcommunication.screen.out_call

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.social.socialcommunication.R
import com.social.socialcommunication.common.Constant
import com.social.socialcommunication.common.ImageUtils
import com.social.socialcommunication.common.SharedPrefUtils
import com.social.socialcommunication.model.User
import com.social.socialcommunication.screen.main.MainActivity
import com.stringee.call.StringeeCall
import kotlinx.android.synthetic.main.activity_outgoing_call.*
import org.json.JSONObject

class OutGoingCallActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        fun newInstance(
            context: Context,
            userIdSender: String,
            userIdReceiver: String,
            receiver: User,
            isVideoCall: Boolean
        ): Intent {
            val intent = Intent(context, OutGoingCallActivity::class.java)
            intent.putExtra(Constant.CALL_FROM, userIdSender)
            intent.putExtra(Constant.CALL_TO, userIdReceiver)
            intent.putExtra(Constant.RECEIVER, receiver)
            intent.putExtra(Constant.IS_VIDEOCALL, isVideoCall)
            return intent
        }
    }

    private var userIdSender: String = ""
    private var userIdReceiver: String = ""
    private var receiver: User? = null
    private var stringeeCall: StringeeCall? = null
    private var mediaState: StringeeCall.MediaState? = null
    private var isVideoCall: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_outgoing_call)
        setEventClick()
        initView()
        makeCall()
    }

    private fun makeCall() {
        stringeeCall = StringeeCall(this, MainActivity.stringeeClient, userIdSender, userIdReceiver)
        stringeeCall?.isVideoCall = isVideoCall!!
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
                runOnUiThread {
                    mediaState = mediaState
                    if (mediaState === StringeeCall.MediaState.CONNECTED) {
                        if (mediaState === StringeeCall.SignalingState.ANSWERED) {
                            tvStatusCallOut.text = "Started"
                        }
                    }
                }
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
                                tvStatusCallOut.text = "Đang đổ chuông"
                            }
                            StringeeCall.SignalingState.ANSWERED -> {
                                if (isVideoCall!!) {
                                    view_remove.visibility = View.VISIBLE
                                    view_local.visibility = View.VISIBLE
                                } else {
                                    tvStatusCallOut.text = "Đang trong cuộc gọi"
                                }
                            }
                            StringeeCall.SignalingState.BUSY -> {
                                tvStatusCallOut.text = "Người dùng bận"
                                finish()
                            }
                            StringeeCall.SignalingState.ENDED -> {
                                if (view_remove.visibility == View.VISIBLE || view_local.visibility == View.VISIBLE) {
                                    view_remove.visibility = View.GONE
                                    view_local.visibility = View.GONE
                                }
                                tvStatusCallOut.text = "Kết thúc"
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
                runOnUiThread {
                    kotlin.run {
                        Toast.makeText(
                            this@OutGoingCallActivity,
                            p2.toString() + ", code" + p1,
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }
                }
            }

        })
        stringeeCall?.makeCall()
    }

    private fun initView() {
        if (intent != null) {
            val avtSender = SharedPrefUtils.getInstance(this).getAvatar()
            receiver = intent.getSerializableExtra(Constant.RECEIVER) as User
            userIdSender = intent.getStringExtra(Constant.CALL_FROM).toString()
            userIdReceiver = intent.getStringExtra(Constant.CALL_TO).toString()
            isVideoCall = intent.getBooleanExtra(Constant.IS_VIDEOCALL, false)
            ImageUtils.loadImage(this, image_avatar_receiver, Uri.parse(receiver?.avatar))
            ImageUtils.loadImage(this, image_avatar_sender, avtSender)
            tvNameReceiver.text = receiver?.nameUser.toString()
        }
    }

    private fun setEventClick() {
        btnBack.setOnClickListener(this)
        btnOutCall.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnOutCall, R.id.btnBack -> {
                if (stringeeCall != null) {
                    stringeeCall?.hangup()
                }
                finish()
            }
        }
    }
}