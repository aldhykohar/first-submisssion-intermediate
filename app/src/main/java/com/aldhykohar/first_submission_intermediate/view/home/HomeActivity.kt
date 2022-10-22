package com.aldhykohar.first_submission_intermediate.view.home

import com.aldhykohar.first_submission_intermediate.base.BaseActivity
import com.aldhykohar.first_submission_intermediate.databinding.ActivityHomeBinding
import com.aldhykohar.first_submission_intermediate.utils.UtilCoroutines

class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    override fun getViewBinding() = ActivityHomeBinding.inflate(layoutInflater)

    override fun initView() {
        binding.logoutMB.setOnClickListener { UtilCoroutines.main { dataStore?.clearDataStore() } }
    }

    override fun initObservers() {

    }
}