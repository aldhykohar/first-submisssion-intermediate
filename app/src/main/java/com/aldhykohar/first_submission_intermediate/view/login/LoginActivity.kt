package com.aldhykohar.first_submission_intermediate.view.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aldhykohar.first_submission_intermediate.R
import com.aldhykohar.first_submission_intermediate.databinding.ActivityLoginBinding
import com.aldhykohar.first_submission_intermediate.utils.UtilExtensions.openActivity
import com.aldhykohar.first_submission_intermediate.view.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.registerMB.setOnClickListener { openActivity(RegisterActivity::class.java) }
    }
}