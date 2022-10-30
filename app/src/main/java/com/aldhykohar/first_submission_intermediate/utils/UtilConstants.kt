package com.aldhykohar.first_submission_intermediate.utils

import android.Manifest

/**
 * Created by aldhykohar on 10/16/2022.
 */

object UtilConstants {
    // STRING
    const val DATA_STORE_NAME = "STORY_DATA_STORE"
    const val LOG_MESSAGE = "LOG_MESSAGE"
    const val NO_INTERNET = "NO_INTERNET"

    //INT
    const val OTHER_ERROR_MESSAGE = "Something wrong"
    const val OTHER_ERROR = 999
    const val ZERO_DATA = 0
    const val CODE_SUCCESS = 200

    const val CAMERA_X_RESULT = 200
    val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    const val REQUEST_CODE_PERMISSIONS = 10
    const val FILENAME_FORMAT = "dd-MMM-yyyy"
    const val DETAIL = "DETAIL"
}