package com.aldhykohar.first_submission_intermediate.view.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aldhykohar.first_submission_intermediate.data.local.DataStoreManager
import com.aldhykohar.first_submission_intermediate.data.model.SuccessResponse
import com.aldhykohar.first_submission_intermediate.data.model.login.LoginRequest
import com.aldhykohar.first_submission_intermediate.data.model.login.LoginResponse
import com.aldhykohar.first_submission_intermediate.data.model.register.RegisterRequest
import com.aldhykohar.first_submission_intermediate.data.network.DataResource
import com.aldhykohar.first_submission_intermediate.data.repository.DataRepository
import kotlinx.coroutines.launch


/**
 * Created by aldhykohar on 10/16/2022.
 */
class AuthViewModel : ViewModel() {
    private var repository: DataRepository = DataRepository()

    private val _registerResponse: MutableLiveData<DataResource<SuccessResponse>> =
        MutableLiveData()
    val registerResponse: LiveData<DataResource<SuccessResponse>> = _registerResponse

    private val _loginResponse: MutableLiveData<DataResource<LoginResponse>> =
        MutableLiveData()
    val loginResponse: LiveData<DataResource<LoginResponse>> = _loginResponse

    fun register(request: RegisterRequest) = viewModelScope.launch {
        _registerResponse.value = DataResource.Loading
        _registerResponse.value = repository.register(request)
    }

    fun login(request: LoginRequest) = viewModelScope.launch {
        _loginResponse.value = DataResource.Loading
        _loginResponse.value = repository.login(request)
    }
}