package com.aldhykohar.first_submission_intermediate.view.auth.splash

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.aldhykohar.first_submission_intermediate.base.BaseActivity
import com.aldhykohar.first_submission_intermediate.databinding.ActivitySplashScreenBinding
import com.aldhykohar.first_submission_intermediate.utils.UtilCoroutines
import com.aldhykohar.first_submission_intermediate.utils.UtilExtensions.openActivity
import com.aldhykohar.first_submission_intermediate.view.auth.login.LoginActivity
import com.aldhykohar.first_submission_intermediate.view.home.HomeActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding>() {
    override fun getViewBinding() = ActivitySplashScreenBinding.inflate(layoutInflater)

    override fun initView() {
    }

    override fun initObservers() {
        UtilCoroutines.main {
            delay(2000)
            openActivity(if (dataStore?.getIsLogin?.first() == true) HomeActivity::class.java else LoginActivity::class.java)
            finish()
        }
    }
}