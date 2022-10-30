package com.aldhykohar.first_submission_intermediate.data.repository

import com.aldhykohar.first_submission_intermediate.base.BaseRepository
import com.aldhykohar.first_submission_intermediate.data.model.list_story.ListStoryRequest
import com.aldhykohar.first_submission_intermediate.data.model.login.LoginRequest
import com.aldhykohar.first_submission_intermediate.data.model.register.RegisterRequest
import com.aldhykohar.first_submission_intermediate.utils.UtilExtensions.toRequestBody
import okhttp3.MultipartBody
import java.io.File


/**
 * Created by aldhykohar on 10/16/2022.
 */
class DataRepository : BaseRepository() {

    suspend fun register(request: RegisterRequest) = safeApiCall { apiService.register(request) }

    suspend fun login(request: LoginRequest) = safeApiCall { apiService.login(request) }

    suspend fun getStory(token: String, request: ListStoryRequest) =
        safeApiCall { apiService.getStory("Bearer $token", request.size, request.page) }

    suspend fun postStory(token: String, file: MultipartBody.Part, desc: String) =
        safeApiCall { apiService.postStory("Bearer $token", file, desc.toRequestBody()) }
}