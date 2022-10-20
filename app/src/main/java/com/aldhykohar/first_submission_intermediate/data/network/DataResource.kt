package com.aldhykohar.first_submission_intermediate.data.network

import okhttp3.ResponseBody

/**
 * Created by aldhykohar on 10/16/2022.
 */

sealed class DataResource<out T> {
    data class Success<out T>(val value: T) : DataResource<T>()
    data class Error(
        val errorCode: Int?,
        val errorBody: ResponseBody?,
        val otherMessage: String?
    ) : DataResource<Nothing>()

    object Loading : DataResource<Nothing>()
}
