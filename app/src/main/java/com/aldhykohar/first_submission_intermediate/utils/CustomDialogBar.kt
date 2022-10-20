package com.aldhykohar.first_submission_intermediate.utils

import android.app.Dialog
import android.content.Context
import com.aldhykohar.first_submission_intermediate.R


/**
 * Created by aldhykohar on 3/26/2021.
 */
class CustomDialogBar {

    lateinit var dialog: CustomDialog


    fun showProgress(context: Context): Dialog {
        dialog = CustomDialog(context)
        dialog.setContentView(R.layout.dialog_progress_bar)
        dialog.show()
        return dialog
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