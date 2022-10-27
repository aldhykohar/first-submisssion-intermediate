package com.aldhykohar.first_submission_intermediate.view.home

import android.app.Application
import androidx.lifecycle.*
import com.aldhykohar.first_submission_intermediate.data.local.DataStoreManager
import com.aldhykohar.first_submission_intermediate.data.model.SuccessResponse
import com.aldhykohar.first_submission_intermediate.data.model.list_story.ListStoryRequest
import com.aldhykohar.first_submission_intermediate.data.model.list_story.ListStoryResponse
import com.aldhykohar.first_submission_intermediate.data.model.login.LoginRequest
import com.aldhykohar.first_submission_intermediate.data.model.login.LoginResponse
import com.aldhykohar.first_submission_intermediate.data.model.login.LoginResult
import com.aldhykohar.first_submission_intermediate.data.model.register.RegisterRequest
import com.aldhykohar.first_submission_intermediate.data.network.DataResource
import com.aldhykohar.first_submission_intermediate.data.repository.DataRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


/**
 * Created by aldhykohar on 10/16/2022.
 */
class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: DataRepository = DataRepository()
    private var dataStore: DataStoreManager = DataStoreManager(application)

    private val _storyResponse: MutableLiveData<DataResource<ListStoryResponse>> =
        MutableLiveData()
    val storyResponse: LiveData<DataResource<ListStoryResponse>> = _storyResponse

    private val _dataUser: MutableLiveData<LoginResult> =
        MutableLiveData()
    val dataUser: LiveData<LoginResult> = _dataUser

    private var token = String()

    init {
        getToken()
    }

    private fun getToken() = viewModelScope.launch {
        val data: LoginResult = dataStore.getDataUser.first()
        token = data.token ?: ""
        _dataUser.value = data
    }

    fun getStory(request: ListStoryRequest) = viewModelScope.launch {
        _storyResponse.value = DataResource.Loading
        _storyResponse.value = repository.getStory(token, request)
    }
}