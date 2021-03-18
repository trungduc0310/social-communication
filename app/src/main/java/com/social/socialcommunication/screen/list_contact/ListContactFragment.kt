package com.social.socialcommunication.screen.list_contact

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.social.socialcommunication.R
import com.social.socialcommunication.base.OnItemClickListener
import com.social.socialcommunication.base.fragment.BaseFragment
import com.social.socialcommunication.base.fragment.FragmentPresenter
import com.social.socialcommunication.base.fragment.FragmentViewOps
import com.social.socialcommunication.model.PhoneBook
import kotlinx.android.synthetic.main.fragment_list_contact.*
import java.io.BufferedInputStream

class ListContactFragment : Fragment(), View.OnClickListener {
    companion object {
        private val PERMISSIONS_REQUEST_READ_CONTACTS = 100
        fun newInstance(): ListContactFragment {
            return ListContactFragment()
        }
    }

    private lateinit var phones: Cursor
    private var listContact: ArrayList<PhoneBook> = ArrayList()
    private lateinit var listContactAdapter: ListContactAdapter
    private lateinit var loadContact: LoadContact

    inner class LoadContact :
        AsyncTask<Void?, Void?, Void?>() {
        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg p0: Void?): Void? {
            // Get Contact list from Phone
            if (phones.count > 0) {
                if (phones != null) {
                    Log.e("count", "" + phones.count)
                    if (phones.count == 0) {
                    }
                    while (phones.moveToNext()) {
                        var bit_thumb: Bitmap? = null
                        val id: String =
                            phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID))
                        val name: String =
                            phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                        val phoneNumber: String =
                            phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        val my_contact_Uri = Uri.withAppendedPath(
                            ContactsContract.Contacts.CONTENT_URI,
                            id
                        )
                        val photo_stream =
                            ContactsContract.Contacts.openContactPhotoInputStream(
                                activity?.contentResolver,
                                my_contact_Uri
                            )
                        val buf = BufferedInputStream(photo_stream)
                        bit_thumb = BitmapFactory.decodeStream(buf)
                        val selectUser = PhoneBook()
                        selectUser.name = name
                        selectUser.phone = phoneNumber.replace("+84", "0").replace(" ", "")
                        selectUser.photo = bit_thumb
                        listContact.add(selectUser)
                    }
                    phones.close()
                } else {
                    Log.e("Cursor close 1", "----------------")
                }
            }
            phones.close()
            return null
        }

        override fun onPostExecute(aVoid: Void?) {
            super.onPostExecute(aVoid)
            //setAdapterWith listContact
            if (listContact.isEmpty()) {
                imgEmptyData.visibility = View.VISIBLE
            } else {
                imgEmptyData.visibility = View.GONE
                listContactAdapter.setListContact(listContact)
            }
            swipeRefreshLayout.isRefreshing = false
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =
            LayoutInflater.from(context!!)
                .inflate(R.layout.fragment_list_contact, container, false);
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setEventClick()
        rcvListContact.layoutManager =
            LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        listContactAdapter = ListContactAdapter()
        listContactAdapter.setItemClickListener(object : OnItemClickListener<PhoneBook> {
            override fun itemClickListener(position: Int, value: PhoneBook) {

            }

        })
        rcvListContact.adapter = listContactAdapter
        swipeRefreshLayout.setOnRefreshListener {
            if (listContact.isNotEmpty()) {
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun setEventClick() {
        btnOpenContact.setOnClickListener(this)
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity!!.checkSelfPermission(
                Manifest.permission.READ_CONTACTS
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_CONTACTS),
                PERMISSIONS_REQUEST_READ_CONTACTS
            )
        } else {
            showContacts()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showContacts()
            }
        }
    }

    private fun showContacts() {
        phones = activity!!.applicationContext.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )!!
        loadContact = LoadContact()
        loadContact.execute()
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnOpenContact -> {
                checkPermission()
            }
        }
    }
}