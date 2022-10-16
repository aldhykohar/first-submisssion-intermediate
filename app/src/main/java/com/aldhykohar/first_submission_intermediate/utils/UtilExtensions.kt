package com.aldhykohar.first_submission_intermediate.utils

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast


object UtilExtensions {
    fun <T> Context.openActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
        val intent = Intent(this, it)
        intent.putExtras(Bundle().apply(extras))
        startActivity(intent)
    }

    fun View.isAreVisible(isVisible: Boolean) {
        visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    fun Context.myToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun Context.myError(message: String) {
        Toast.makeText(this, "ERROR : $message", Toast.LENGTH_LONG).show()
    }

    fun EditText.setTextEditable(text: String) {
        this.text = Editable.Factory.getInstance().newEditable(text)
    }

    fun TextView.setPaintFlag() {
        this.paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }

    fun String.getAlphabetQuestionImage(): String {
        if (this.isEmpty()) return this
        return this.substring(0, 2)
    }

    fun String.getQuestionImageTitle(): String {
        if (this.isEmpty()) return this
        return this.substring(3, this.length)
    }

    fun String?.toSpanned(): Spanned {
        return when {
            this == null -> SpannableString("")
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> Html.fromHtml(
                this,
                Html.FROM_HTML_MODE_LEGACY
            )
            else -> {
                @Suppress("DEPRECATION")
                return Html.fromHtml(this)
            }
        }
    }
}