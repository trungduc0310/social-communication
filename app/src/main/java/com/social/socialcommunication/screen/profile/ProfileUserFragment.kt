package com.social.socialcommunication.screen.profile

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.social.socialcommunication.R
import com.social.socialcommunication.base.fragment.BaseFragment
import com.social.socialcommunication.base.fragment.FragmentPresenter
import com.social.socialcommunication.base.fragment.FragmentViewOps
import com.social.socialcommunication.common.ImageUtils
import com.social.socialcommunication.model.User
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

    override fun setUp(view: View) {
        setEventClick()
        setDataOnView()
        setListSetting()
    }

    private fun setDataOnView() {
        val avtPath = userInfo?.getAvatar().toString()
        val userName = userInfo?.nameUser.toString()
        ImageUtils.loadImage(context!!, imgUserAvatar, avtPath)
        tvUserName.text = userName
    }

    private fun setEventClick() {
        btnBack.setOnClickListener(this)
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
        TODO("Not yet implemented")
    }

    override fun onSupport() {
        TODO("Not yet implemented")
    }

    override fun onPolicy() {
        TODO("Not yet implemented")
    }

    override fun onIntroApp() {
        TODO("Not yet implemented")
    }

    override fun onShareApp() {
        TODO("Not yet implemented")
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnBack -> {
                getActivityReference()?.onBackPressed()
            }
        }
    }
}