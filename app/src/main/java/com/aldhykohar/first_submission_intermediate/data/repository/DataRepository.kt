package com.aldhykohar.first_submission_intermediate.data.repository

import com.aldhykohar.first_submission_intermediate.base.BaseRepository
import com.aldhykohar.first_submission_intermediate.data.model.list_story.ListStoryRequest
import com.aldhykohar.first_submission_intermediate.data.model.login.LoginRequest
import com.aldhykohar.first_submission_intermediate.data.model.register.RegisterRequest


/**
 * Created by aldhykohar on 10/16/2022.
 */
class DataRepository : BaseRepository() {

    suspend fun register(request: RegisterRequest) = safeApiCall { apiService.register(request) }

    suspend fun login(request: LoginRequest) = safeApiCall { apiService.login(request) }

    suspend fun getStory(token: String, request: ListStoryRequest) =
        safeApiCall { apiService.getStory("Bearer $token", request.size, request.page) }
}