package com.aldhykohar.first_submission_intermediate.data.repository

import com.aldhykohar.first_submission_intermediate.base.BaseRepository
import com.aldhykohar.first_submission_intermediate.data.model.register.RegisterRequest


/**
 * Created by aldhykohar on 10/16/2022.
 */
class DataRepository : BaseRepository() {

    suspend fun register(request: RegisterRequest) = safeApiCall { apiService.register(request) }
}