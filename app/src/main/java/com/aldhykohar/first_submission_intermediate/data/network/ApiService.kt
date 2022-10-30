package com.aldhykohar.first_submission_intermediate.data.network

import com.aldhykohar.first_submission_intermediate.data.model.SuccessResponse
import com.aldhykohar.first_submission_intermediate.data.model.list_story.ListStoryRequest
import com.aldhykohar.first_submission_intermediate.data.model.list_story.ListStoryResponse
import com.aldhykohar.first_submission_intermediate.data.model.login.LoginRequest
import com.aldhykohar.first_submission_intermediate.data.model.login.LoginResponse
import com.aldhykohar.first_submission_intermediate.data.model.register.RegisterRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


/**
 * Created by aldhykohar on 10/16/2022.
 */
interface ApiService {
    @POST("register")
    suspend fun register(@Body request: RegisterRequest): SuccessResponse

    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("stories")
    suspend fun getStory(
        @Header("authorization") token: String,
        @Query("size") size: Int,
        @Query("page") page: Int,
    ): ListStoryResponse

    @POST("stories")
    @Multipart
    suspend fun postStory(
        @Header("authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): SuccessResponse
}