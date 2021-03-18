package com.social.socialcommunication.screen.list_contact

import com.google.firebase.database.*
import com.social.socialcommunication.base.fragment.FragmentPresenter
import com.social.socialcommunication.model.User

class ListContactPresenter : FragmentPresenter<ListContactViewOps.ViewOps>(),
    ListContactViewOps.PresenterViewOps {
    private lateinit var mReference: DatabaseReference
    override fun onCreate() {
        super.onCreate()
    }

    override fun readUser(idUserLogin: String) {
        getView()?.showProgressbar()
        mReference = FirebaseDatabase.getInstance().getReference("Users")
        var userList: ArrayList<User> = ArrayList()
        mReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                getView()?.hideProgressbar()
                getView()?.getListUserFail(error.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                getView()?.hideProgressbar()
                for (chil in snapshot.children) {
                    val user = chil.getValue(User::class.java)
                    if (!user?.idUser.equals(idUserLogin)) {
                        userList.add(user!!)
                    }
                }
                getView()?.getListUserSuccess(userList)
            }

        })
    }


}