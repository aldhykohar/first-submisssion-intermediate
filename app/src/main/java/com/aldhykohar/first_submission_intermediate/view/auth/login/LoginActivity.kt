package com.aldhykohar.first_submission_intermediate.view.auth.login

import androidx.activity.viewModels
import com.aldhykohar.first_submission_intermediate.R
import com.aldhykohar.first_submission_intermediate.base.BaseActivity
import com.aldhykohar.first_submission_intermediate.data.model.login.LoginRequest
import com.aldhykohar.first_submission_intermediate.data.model.login.LoginResponse
import com.aldhykohar.first_submission_intermediate.data.network.DataResource
import com.aldhykohar.first_submission_intermediate.databinding.ActivityLoginBinding
import com.aldhykohar.first_submission_intermediate.utils.UtilExtensions.myError
import com.aldhykohar.first_submission_intermediate.utils.UtilExtensions.myToast
import com.aldhykohar.first_submission_intermediate.utils.UtilExtensions.openActivity
import com.aldhykohar.first_submission_intermediate.view.auth.AuthViewModel
import com.aldhykohar.first_submission_intermediate.view.auth.register.RegisterActivity
import com.aldhykohar.first_submission_intermediate.view.home.HomeActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    private val viewModel by viewModels<AuthViewModel>()
    override fun getViewBinding() = ActivityLoginBinding.inflate(layoutInflater)

    override fun initView() {
        initClick()
    }

    private fun initClick() {
        with(binding) {
            registerMB.setOnClickListener { openActivity(RegisterActivity::class.java) }
            loginMB.setOnClickListener {
                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()

                if (email.isEmpty()) {
                    emailEditText.error = getString(R.string.field_cannot_blank)
                } else {
                    viewModel.login(LoginRequest(email, password))
                }
            }
        }
    }

    override fun initObservers() {
        viewModel.loginResponse.observe(this) {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> updateUI(it.value)
                is DataResource.Error -> handleError(it.errorBody)
            }
        }
    }

    private fun updateUI(value: LoginResponse) {
        showLoading(false)
        if (value.error == false) {
            openActivity(HomeActivity::class.java)
        } else {
            myError(value.message ?: "")
        }
    }
}