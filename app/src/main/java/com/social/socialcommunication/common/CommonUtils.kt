package com.social.socialcommunication.common

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
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

        fun getCurrentDate(): String? {
            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
            val date = Calendar.getInstance().time
            return dateFormat.format(date)
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

        fun hideKeyboard(
            context: Context,
            view: View?
        ) {
            if (view != null) {
                val imm =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

        fun showKeyboard(
            context: Context,
            view: View?
        ) {
            if (view != null) {
                val imm =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm?.showSoftInput(
                    view,
                    InputMethodManager.SHOW_IMPLICIT
                )
            }
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
            input: String?,
            format: String?
        ): String? {
            return convertDateStringToString(input, format, "MMM dd")
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
            input: String?,
            format: String?
        ): Date? {
            val simpleDateFormat = SimpleDateFormat(format)
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
    }
}