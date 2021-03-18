package com.social.socialcommunication.common

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.social.socialcommunication.R
import com.social.socialcommunication.custom_view.CustomTextviewFont
import com.social.socialcommunication.dialog.OnButtonClickListener
import java.math.BigDecimal
import java.text.*
import java.util.*

class CommonUtils {
    companion object {
        fun hideSoftKeyboard(activity: Activity) {
            if (activity == null)
                return
            val view = activity.currentFocus
            if (view != null) {
                val imm =
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
                view.clearFocus()
            }
        }

        fun showKeyboard(context: Context, editText: EditText) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        }

        fun hideSoftKeyboard(view: View, activity: Activity) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            view.clearFocus()
        }


        fun convertDate(date: Date?): String? {
            var formatDate: String? = null
            try {
                val df = SimpleDateFormat("dd/MM/yyyy")
                formatDate = df.format(date)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return formatDate
        }

        fun convertTime(date: Date?): String? {
            var formatDate: String? = null
            try {
                val df = SimpleDateFormat("HH:mm")
                formatDate = df.format(date)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return formatDate
        }

        fun getCurrentDateString(): String? {
            val dateFormat: DateFormat =
                SimpleDateFormat(Constant.APP_DEFAULT_HOUR_FORMAT)
            val date = Calendar.getInstance().time
            return dateFormat.format(date)
        }

        fun getDateStringtoDate(date: String): Date? {
            val dateFormat: DateFormat = SimpleDateFormat(Constant.APP_DEFAULT_HOUR_FORMAT)
            return try {
                dateFormat.parse(date)
            } catch (ex: Exception) {
                null
            }
        }

        fun getCurrentDate(): Date? {
            val date = Calendar.getInstance().time
            return date
        }


        @Throws(ParseException::class)
        fun formatDate(
            date: String?,
            initDateFormat: String?,
            endDateFormat: String?
        ): String? {
            val initDate = SimpleDateFormat(initDateFormat).parse(date)
            val formatter = SimpleDateFormat(endDateFormat)
            return formatter.format(initDate)
        }

        @Throws(ParseException::class)
        fun convertDateToInt(date: Date): Int {
            return (date.time / 1000).toInt()
        }

        fun showHideKeyBoard(
            context: Context,
            isShow: Boolean,
            editText: EditText
        ) {
            val inputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (isShow) {
                inputMethodManager.showSoftInput(
                    editText,
                    InputMethodManager.RESULT_UNCHANGED_SHOWN
                )
            } else {
                inputMethodManager.hideSoftInputFromWindow(
                    editText.windowToken,
                    InputMethodManager.RESULT_UNCHANGED_SHOWN
                )
            }
        }


        @Throws(ParseException::class)
        fun compareDate(startDate: String?, endDate: String?): Boolean {
            val simpleDateFormat =
                SimpleDateFormat("dd/MM/yyyy")
            val start = simpleDateFormat.parse(startDate)
            val end = simpleDateFormat.parse(endDate)
            return start.before(end) || start == end
        }

        fun convertCurrency(money: Int): String? {
            val newmoney =
                DecimalFormat("###,###.###").format(money.toDouble())
            return newmoney.replace(",".toRegex(), ".")
        }

        fun convertNumberToPrice(price: Double, unit: String): String? {
            val decimal = BigDecimal(price)
            return (convertNumberToString(decimal) + " " + unit).trim { it <= ' ' }
        }

        fun convertNumberToString(number: BigDecimal?): String {
            val formatter = NumberFormat.getInstance(
                Locale(
                    "vi",
                    "VN"
                )
            ) as DecimalFormat
            return formatter.format(number)
        }

        fun convertDateStringToString(
            input: String?
        ): String? {
            return convertDateStringToString(
                input,
                "EEE MMM dd HH:mm:ss zzz yyyy",
                Constant.APP_DEFAULT_HOUR_FORMAT
            )
        }

        fun convertDateStringToString(
            input: String?,
            inFormat: String?,
            outFormat: String?
        ): String? {
            return convertDateStringToString(
                input,
                inFormat,
                outFormat,
                TimeZone.getTimeZone("GMT"),
                TimeZone.getDefault()
            )
        }

        fun convertDateStringToString(
            input: String?, inFormat: String?, outFormat: String?,
            timeZoneIn: TimeZone?, timeZoneOut: TimeZone?
        ): String? {
            if (TextUtils.isEmpty(input) || TextUtils.isEmpty(inFormat) || TextUtils.isEmpty(
                    outFormat
                )
            ) {
                return ""
            }
            val simpleDateFormat =
                SimpleDateFormat(inFormat, Locale.UK)
            simpleDateFormat.timeZone = timeZoneIn
            try {
                val date = simpleDateFormat.parse(input)
                val sdfoutput =
                    SimpleDateFormat(outFormat, Locale.UK)
                sdfoutput.timeZone = timeZoneOut
                return sdfoutput.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return ""
        }

        fun convertDateToString(
            date: Date?,
            format: String?
        ): String? {
            val SDFoutput =
                SimpleDateFormat(format, Locale.getDefault())
            return SDFoutput.format(date)
        }


        fun convertStringToDate(
            input: String?
        ): Date? {
            val simpleDateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
            try {
                return simpleDateFormat.parse(input)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return null
        }

        fun getDateFromString(time: String?): Date? {
            @SuppressLint("SimpleDateFormat") val simpleDateFormat =
                SimpleDateFormat("dd/MM/yy")
            var date: Date? = null
            try {
                date = simpleDateFormat.parse(time)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return date
        }

        fun hasConnected(context: Context?): Boolean {
            if (context == null) return true
            val check =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = check.allNetworkInfo
            for (networkInfo in info) if (networkInfo.state == NetworkInfo.State.CONNECTED) return true
            return false
        }

        fun showPopupButton(
            context: Context,
            view: View,
            resoure: Int,
            buttonContent: String,
            buttonClick: OnButtonClickListener<Any?>
        ) {
            val mInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = mInflater.inflate(resoure, null)
            layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            val popupWindow = PopupWindow(layout, 250, FrameLayout.LayoutParams.WRAP_CONTENT, true)
            val btnAccept = layout.findViewById<LinearLayout>(R.id.btnAccept)
            val content = layout.findViewById<CustomTextviewFont>(R.id.btnContentPopup)
            content.text = buttonContent
            popupWindow.showAsDropDown(view, 100, 5)
            btnAccept.setOnClickListener {
                buttonClick.onAcceptClickListener(null)
                popupWindow.dismiss()
            }
        }
    }
}