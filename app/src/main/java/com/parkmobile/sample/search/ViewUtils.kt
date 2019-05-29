package com.parkmobile.sample.search

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun showSoftKeyboard(context: Context, view: View) {
    view.requestFocus()
    view.post {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun hideSoftKeyboard(context: Context, view: View) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}