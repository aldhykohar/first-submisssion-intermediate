package com.aldhykohar.first_submission_intermediate.view.auth.register

import android.widget.Toast
import androidx.activity.viewModels
import com.aldhykohar.first_submission_intermediate.R
import com.aldhykohar.first_submission_intermediate.base.BaseActivity
import com.aldhykohar.first_submission_intermediate.data.model.SuccessResponse
import com.aldhykohar.first_submission_intermediate.data.model.register.RegisterRequest
import com.aldhykohar.first_submission_intermediate.data.network.DataResource
import com.aldhykohar.first_submission_intermediate.databinding.ActivityRegisterBinding
import com.aldhykohar.first_submission_intermediate.utils.UtilExtensions.myError
import com.aldhykohar.first_submission_intermediate.utils.UtilExtensions.myToast
import com.aldhykohar.first_submission_intermediate.view.auth.AuthViewModel

class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {

    private val viewModel by viewModels<AuthViewModel>()

    override fun getViewBinding() = ActivityRegisterBinding.inflate(layoutInflater)

    override fun initView() {
        initClick()
    }

    private fun initClick() {
        with(binding) {
            floatingActionButton.setOnClickListener { onBackPress() }
            registerMB.setOnClickListener {
                val name = nameEditText.text.toString()
                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()

                if (name.isEmpty()) {
                    nameEditText.error = getString(R.string.field_cannot_blank)
                } else if (email.isEmpty()) {
                    emailEditText.error = getString(R.string.field_cannot_blank)
                } else {
                    viewModel.register(RegisterRequest(name, email, password))
                }
            }
        }
    }

    override fun initObservers() {
        viewModel.registerResponse.observe(this) {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> updateUI(it.value)
                is DataResource.Error -> handleError(it.errorBody)
            }
        }
    }

    private fun updateUI(value: SuccessResponse) {
        showLoading(false)
        if (value.error == false) {
            myToast(value.message ?: "")
            finish()
        } else {
            myError(value.message ?: "")
        }
    }
}