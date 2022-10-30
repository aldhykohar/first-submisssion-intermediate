package com.aldhykohar.first_submission_intermediate.utils

import android.app.Dialog
import android.content.Context
import com.aldhykohar.first_submission_intermediate.R


/**
 * Created by aldhykohar on 3/26/2021.
 */
class CustomDialogBar(context: Context) {

    lateinit var dialog: CustomDialog

    init {
        dialog = CustomDialog(context)
        dialog.setContentView(R.layout.dialog_progress_bar)
    }


    fun showProgress() {
        dialog.show()
    }

    fun hide() {
        dialog.dismiss()
    }

    class CustomDialog
        (context: Context) : Dialog(context, R.style.CustomDialogTheme) {
        init {
            // Set Semi-Transparent Color for Dialog Background
            window?.decorView?.rootView?.setBackgroundResource(R.color.colorBlackTransparent)
            window?.decorView?.setOnApplyWindowInsetsListener { _, insets ->
                insets.consumeSystemWindowInsets()
            }
        }
    }
}