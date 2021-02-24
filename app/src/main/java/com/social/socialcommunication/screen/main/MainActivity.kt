package com.social.socialcommunication.screen.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.social.socialcommunication.screen.list_chat.ListChatFragment
import com.social.socialcommunication.screen.list_contact.ListContactFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainViewOps.PresenterViewOps>(), MainViewOps.ViewOps,
    View.OnClickListener {

    private var mainPagerAdapter: MainPagerAdapter? = null
    override fun getViewResoure(): Int {
        return R.layout.activity_main
    }

    override fun setUp() {
        setUpViewPager()
        setEventClick()
    }

    private fun setEventClick() {
        tabChat.setOnClickListener(this)
        tabContact.setOnClickListener(this)
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
                //TODO Chat fragment
            }
            1 -> {
                //TODO Contact fragment
            }
        }
    }

    override fun onRegisterPresenter(): Class<out ActivityPresenter<ActivityViewOps>> {
        return MainPresenter::class.java
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
                    ListContactFragment.newInstance()
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
                //TODO tabChatClick
            }
            R.id.tabContact -> {
                //TODO tabContactClick
            }
            R.id.btnAddConversation -> {
                //TODO addConversation(Chat) addContact(Contact)
            }
        }
    }
}