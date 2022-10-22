package com.aldhykohar.first_submission_intermediate.base

import com.aldhykohar.first_submission_intermediate.data.local.DataStoreManager
import com.aldhykohar.first_submission_intermediate.data.network.ApiClient
import com.aldhykohar.first_submission_intermediate.data.network.ApiService
import com.aldhykohar.first_submission_intermediate.data.network.DataResource
import com.aldhykohar.first_submission_intermediate.utils.UtilConstants.OTHER_ERROR
import com.aldhykohar.first_submission_intermediate.utils.UtilConstants.OTHER_ERROR_MESSAGE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException


/**
 * Created by aldhykohar on 10/16/2022.
 */

abstract class BaseRepository {
    var apiService: ApiService = ApiClient().getRetrofit().create(ApiService::class.java)

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): DataResource<T> {
        return withContext(Dispatchers.IO) {
            try {
                DataResource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        DataResource.Error(
                            throwable.code(),
                            throwable.response()?.errorBody(),
                            throwable.message
                        )
                    }
                    else -> DataResource.Error(OTHER_ERROR, null, OTHER_ERROR_MESSAGE)
                }
            }
        }
    }

}