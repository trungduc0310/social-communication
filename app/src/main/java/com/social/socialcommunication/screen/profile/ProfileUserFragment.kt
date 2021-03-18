package com.social.socialcommunication.screen.profile

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.social.socialcommunication.R
import com.social.socialcommunication.base.fragment.BaseFragment
import com.social.socialcommunication.base.fragment.FragmentPresenter
import com.social.socialcommunication.base.fragment.FragmentViewOps
import com.social.socialcommunication.common.ImageUtils
import com.social.socialcommunication.model.User
import com.social.socialcommunication.screen.splash.SplashActivity
import com.social.socialcommunication.screen.update_account.UpdateAccountFragment
import kotlinx.android.synthetic.main.fragment_user_info.*

class ProfileUserFragment : BaseFragment<ProfileViewOps.PresenterViewOps>,
    ProfileViewOps.ViewOps, SettingProfileAdapter.OnItemSettingClickListener, View.OnClickListener {
    private var userInfo: User? = null
    private var profileUserAdapter: SettingProfileAdapter? = null

    constructor(userInfo: User?) : super() {
        this.userInfo = userInfo
    }

    companion object {
        fun newInstance(userInfo: User): ProfileUserFragment {
            return ProfileUserFragment(userInfo)
        }
    }


    override fun getViewResoure(): Int {
        return R.layout.fragment_user_info
    }

    override fun setUp() {
        setEventClick()
        setDataOnView()
        setListSetting()
    }

    private fun setDataOnView() {
        val avtPath = Uri.parse(userInfo?.avatar)
        val userName = userInfo?.nameUser.toString()
        ImageUtils.loadImage(context!!, imgUserAvatar, avtPath)
        tvUserName.text = userName
    }

    private fun setEventClick() {
        btnBack.setOnClickListener(this)
        btnLogout.setOnClickListener(this)
    }

    private fun setListSetting() {
        val layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        rcvListSettingProfile.layoutManager = layoutManager
        profileUserAdapter = SettingProfileAdapter()
        profileUserAdapter?.setItemSettingClickListener(this)
        rcvListSettingProfile.adapter = profileUserAdapter
    }

    override fun onRegisterPresenter(): Class<out FragmentPresenter<FragmentViewOps>> {
        return ProfileUserPresenter::class.java as Class<out FragmentPresenter<FragmentViewOps>>
    }

    override fun onEditAccount() {
        replaceFragment(UpdateAccountFragment.newInstance(), true)
    }

    override fun onSupport() {
    }

    override fun onPolicy() {
    }

    override fun onIntroApp() {
    }

    override fun onShareApp() {
        val shareBody = "Mời bạn sử dụng ứng dụng Social Communication"
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        startActivity(sharingIntent)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnBack -> {
                getActivityReference()?.onBackPressed()
            }
            R.id.btnLogout -> {
                mPresenter?.logoutApp(getActivityContext()!!)
                startActivity(SplashActivity.getInstance(getActivityContext()!!))
                getActivityReference()?.finish()
            }
        }
    }
}