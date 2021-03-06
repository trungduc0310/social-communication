package com.social.socialcommunication.screen.main

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.social.socialcommunication.R
import com.social.socialcommunication.base.activity.ActivityPresenter
import com.social.socialcommunication.base.activity.ActivityViewOps
import com.social.socialcommunication.base.activity.BaseActivity
import com.social.socialcommunication.common.ImageUtils
import com.social.socialcommunication.common.SharedPrefUtils
import com.social.socialcommunication.model.User
import com.social.socialcommunication.screen.list_chat.ListChatFragment
import com.social.socialcommunication.screen.list_contact.ListAllChatFragment
import com.social.socialcommunication.screen.list_contact.ListContactFragment
import com.social.socialcommunication.screen.login.LoginFragment
import com.social.socialcommunication.screen.profile.ProfileUserFragment
import com.stringee.StringeeClient
import com.stringee.call.StringeeCall
import com.stringee.exception.StringeeError
import com.stringee.listener.StringeeConnectionListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_header_main.*
import org.json.JSONObject

class MainActivity : BaseActivity<MainViewOps.PresenterViewOps>(), MainViewOps.ViewOps,
    View.OnClickListener {
    companion object {
        var stringeeClient: StringeeClient? = null
        fun newInstance(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }

    }

    private var mainPagerAdapter: MainPagerAdapter? = null
    private var userInfo: User? = null
    override fun getViewResoure(): Int {
        return R.layout.activity_main
    }

    override fun setUp() {
        stringeeClient = StringeeClient(this)
        checkLogin()
    }


    private fun checkLogin() {
        setDataOnView()
        setUpViewPager()
        setEventClick()
    }

    private fun setDataOnView() {
        userInfo = SharedPrefUtils.getInstance(this).getAccount()
        val avatar = SharedPrefUtils.getInstance(this).getAvatar()
        userInfo?.avatar = avatar.toString()
        ImageUtils.loadImage(this, imgUserAvatar, avatar!!)
    }

    private fun setEventClick() {
        tabChat.setOnClickListener(this)
        tabContact.setOnClickListener(this)
        btnAddConversation.setOnClickListener(this)
        imgUserAvatar.setOnClickListener(this)
    }

    private fun setUpViewPager() {
        try {
            mainPagerAdapter = MainPagerAdapter(supportFragmentManager, lifecycle)
            vp_main.adapter = mainPagerAdapter
            vp_main.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    switchTab(position)
                }
            })
        } catch (ex: Exception) {
            recreate()
        }
    }

    private fun switchTab(position: Int) {
        when (position) {
            0 -> {
                selectTabChat()
                unselectTabContact()
            }
            1 -> {
                selectTabContact()
                unselectTabChat()
            }
        }
    }

    private fun selectTabContact() {
        tvViewNameMain.text = resources.getText(R.string.text_contact)
        btnAddConversation.setImageResource(R.drawable.ic_add_member_white)
        imgContact.isEnabled = true
        tvContact.isEnabled = true
    }

    private fun unselectTabContact() {
        imgContact.isEnabled = false
        tvContact.isEnabled = false
    }

    private fun selectTabChat() {
        tvViewNameMain.text = resources.getText(R.string.text_chat)
        btnAddConversation.setImageResource(R.drawable.ic_edit_white)
        imgChat.isEnabled = true
        tvTextChat.isEnabled = true
    }

    private fun unselectTabChat() {
        imgChat.isEnabled = false
        tvTextChat.isEnabled = false
    }

    override fun onRegisterPresenter(): Class<out ActivityPresenter<ActivityViewOps>> {
        return MainPresenter::class.java as Class<out ActivityPresenter<ActivityViewOps>>
    }

    inner class MainPagerAdapter : FragmentStateAdapter {
        constructor(fragmentManager: FragmentManager, lifecycle: Lifecycle) : super(
            fragmentManager,
            lifecycle
        )

        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            var fragment: Fragment? = null
            fragment = when (position) {
                0 -> {
                    ListChatFragment.newInstance()
                }
                1 -> {
                    ListAllChatFragment.newInstance()
                }
                else -> {
                    ListChatFragment.newInstance()
                }
            }
            return fragment!!
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.tabChat -> {
                vp_main.currentItem = 0
            }
            R.id.tabContact -> {
                vp_main.currentItem = 1
            }
            R.id.btnAddConversation -> {
                when (vp_main.currentItem) {
                    0 -> {
                        //TODO add Conversation
                    }
                    1 -> {
                        //TODO add contact
                    }
                }
            }
            R.id.imgUserAvatar -> {
                replaceFragment(ProfileUserFragment.newInstance(userInfo!!), true)
            }
        }
    }

    override fun onBackPressed() {
        hideLoading()
        val fragment = supportFragmentManager.findFragmentById(R.id.main_layout)
        if (fragment is LoginFragment && fragment.isVisible) {
            return
        }
        super.onBackPressed()
    }


}