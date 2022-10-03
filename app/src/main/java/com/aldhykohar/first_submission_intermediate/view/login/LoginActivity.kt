package com.aldhykohar.first_submission_intermediate.view.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aldhykohar.first_submission_intermediate.R
import com.aldhykohar.first_submission_intermediate.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}