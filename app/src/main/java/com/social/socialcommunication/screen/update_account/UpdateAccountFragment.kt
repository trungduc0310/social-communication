package com.social.socialcommunication.screen.update_account

import android.net.Uri
import android.util.Log
import android.view.View
import com.social.socialcommunication.R
import com.social.socialcommunication.base.fragment.BaseFragment
import com.social.socialcommunication.base.fragment.FragmentPresenter
import com.social.socialcommunication.base.fragment.FragmentViewOps
import com.social.socialcommunication.common.CommonUtils
import com.social.socialcommunication.common.ImageUtils
import com.social.socialcommunication.common.SharedPrefUtils
import com.social.socialcommunication.dialog.ChangeAvatarDialog
import com.social.socialcommunication.dialog.ChangeNameDialog
import com.social.socialcommunication.dialog.OnButtonClickListener
import com.social.socialcommunication.screen.change_password.ChangePasswordDialog
import com.tuanfadbg.takephotoutils.TakePhotoCallback
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import kotlinx.android.synthetic.main.fragment_edit_profile.btnBack
import kotlinx.android.synthetic.main.fragment_edit_profile.imgUserAvatar
import kotlinx.android.synthetic.main.fragment_edit_profile.tvUserName
import java.io.File

class UpdateAccountFragment : BaseFragment<UpdateAccountViewOps.PersenterViewOps>(),
    UpdateAccountViewOps.ViewOsp, View.OnClickListener {

    private var uriChangeAvt: Uri? = null

    companion object {
        fun newInstance(): UpdateAccountFragment {
            return UpdateAccountFragment()
        }
    }

    override fun getViewResoure(): Int {
        return R.layout.fragment_edit_profile
    }

    override fun setUp() {
        setEventClick()
        setDataOnView()
    }

    private fun setDataOnView() {
        val account = SharedPrefUtils.getInstance(getActivityContext()!!).getAccount()
        val nameAcc = account?.nameUser.toString()
        val phoneNumber = account?.phoneNumber.toString()
        val email = account?.email.toString()
        val avtPath = Uri.parse(account?.avatar)
        uriChangeAvt = avtPath
        tvUserName.text = nameAcc
        if (phoneNumber.isEmpty()) {
            btnAddPhoneNumber.visibility = View.VISIBLE
        }else{
            btnAddPhoneNumber.visibility=View.GONE
            edtInputPhoneNumber.setText(phoneNumber)
        }
        edtInputEmail.setText(email)
        ImageUtils.loadImage(context!!, imgUserAvatar, avtPath)
    }

    private fun setEventClick() {
        btnEditUserName.setOnClickListener(this)
        btnViewMore.setOnClickListener(this)
        btnBack.setOnClickListener(this)
        imgUserAvatar.setOnClickListener(this)
        btnSave.setOnClickListener(this)
        btnAddPhoneNumber.setOnClickListener(this)
    }

    override fun onRegisterPresenter(): Class<out FragmentPresenter<FragmentViewOps>> {
        return UpdateAccountPresenter::class.java as Class<out FragmentPresenter<FragmentViewOps>>
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnEditUserName -> {
                val nameAcc = tvUserName.text.toString()
                val changeNameDialog = ChangeNameDialog(nameAcc, getActivityContext()!!)
                changeNameDialog.setOnButtonClickListener(object : OnButtonClickListener<String> {
                    override fun onAcceptClickListener(value: String) {
                        tvUserName.text = value
                    }

                    override fun onCancelClickListener() {
                    }

                })
                changeNameDialog.show()
            }
            R.id.btnViewMore -> {
                showPopup()
            }
            R.id.btnBack -> {
                CommonUtils.hideSoftKeyboard(getActivityReference()!!)
                getActivityReference()?.onBackPressed()
            }
            R.id.btnAddPhoneNumber -> {
                showToast("Tính năng sẽ được cập nhật ở phiên bản tiếp theo")
            }
            R.id.imgUserAvatar -> {
                val changeAvatarDialog = ChangeAvatarDialog(getActivityContext()!!)
                changeAvatarDialog.setOnButtonClickListener(object :
                    ChangeAvatarDialog.OnTakeImageListener {
                    override fun onOpenGalery() {
                        getTakePhotoUtils()?.imageFromGallery?.setListener(object :
                            TakePhotoCallback {
                            override fun onSuccess(path: String?, width: Int, height: Int) {
                                uriChangeAvt = Uri.fromFile(File(path))
                                ImageUtils.loadImage(context!!, imgUserAvatar, path)
                            }

                            override fun onFail() {
                                Log.d("onFail: ", "get Fail Image")
                            }

                        })
                    }

                    override fun onOpenCamera() {
                        getTakePhotoUtils()?.takePhoto()?.setListener(object : TakePhotoCallback {
                            override fun onSuccess(path: String?, width: Int, height: Int) {
                                uriChangeAvt = Uri.fromFile(File(path))
                                ImageUtils.loadImage(context!!, imgUserAvatar, path)
                            }

                            override fun onFail() {
                                Log.d("onFail: ", "get Fail Image")
                            }

                        })
                    }

                })
                changeAvatarDialog.show()
            }
            R.id.btnSave -> {
                val user = SharedPrefUtils.getInstance(getActivityContext()!!).getAccount()
                user?.avatar = uriChangeAvt.toString()
                user?.nameUser = tvUserName.text.toString()
                mPresenter?.updateProfile(getActivityContext()!!, user!!)
            }
        }
    }

    private fun showPopup() {
        CommonUtils.showPopupButton(getActivityContext()!!,
            btnViewMore,
            R.layout.layout_custom_single_button_popup,
            "Đổi mật khẩu",
            object : OnButtonClickListener<Any?> {
                override fun onAcceptClickListener(value: Any?) {
                    val changePasswordDialog = ChangePasswordDialog(getActivityContext()!!)
                    changePasswordDialog.setButtonClickListener(object :
                        OnButtonClickListener<String> {
                        override fun onAcceptClickListener(value: String) {
                            mPresenter?.updatePassword(value)
                        }

                        override fun onCancelClickListener() {

                        }

                    })
                    changePasswordDialog.show()
                }

                override fun onCancelClickListener() {
                }

            })
    }

    override fun onUpdateSuccess() {
        showToast("Cập nhật thành công!")
    }

    override fun onUpdateFail(messenger: String) {
        showToast(messenger)
    }
}