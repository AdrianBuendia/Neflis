package com.neflis.neflis.core

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import java.text.NumberFormat
import java.util.*

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Long.convertToTimeFormat(): String {
    if (this < 10L) {
    return "0$this"
    }
    return this.toString()
}

fun Double.convertToMoneyFormat(): String {
    val currencyFormatter = NumberFormat.getCurrencyInstance(
        Locale("es", "MX")
    )
    //val df = DecimalFormat("0.00")
    //df.roundingMode = RoundingMode.CEILING
    return currencyFormatter.format(this)
}


fun String.convertToMoneyFormat(): String {
    val currencyFormatter = NumberFormat.getCurrencyInstance(
        Locale("es", "MX")
    )
    if (this.isEmpty()) {
        return currencyFormatter.format(0.00)
    } else {
        return currencyFormatter.format(this.toDouble())
    }
}
