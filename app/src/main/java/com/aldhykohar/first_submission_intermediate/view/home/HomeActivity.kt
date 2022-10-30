package com.aldhykohar.first_submission_intermediate.view.home

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.NonNull
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.aldhykohar.first_submission_intermediate.base.BaseActivity
import com.aldhykohar.first_submission_intermediate.data.model.list_story.ListStoryRequest
import com.aldhykohar.first_submission_intermediate.data.model.list_story.ListStoryResponse
import com.aldhykohar.first_submission_intermediate.data.network.DataResource
import com.aldhykohar.first_submission_intermediate.databinding.ActivityHomeBinding
import com.aldhykohar.first_submission_intermediate.utils.UtilCoroutines
import com.aldhykohar.first_submission_intermediate.utils.UtilExtensions.isAreVisible
import com.aldhykohar.first_submission_intermediate.utils.UtilExtensions.myError
import com.aldhykohar.first_submission_intermediate.utils.UtilExtensions.myToast
import com.aldhykohar.first_submission_intermediate.utils.UtilExtensions.openActivity
import com.aldhykohar.first_submission_intermediate.view.add_story.AddStoryActivity
import com.aldhykohar.first_submission_intermediate.view.auth.login.LoginActivity
import kotlinx.coroutines.delay

class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    private val viewModel by viewModels<HomeViewModel>()

    private var currentSize = 10
    private var currentPage = 1

    private val adapterStory by lazy {
        ListStoryAdapter()
    }

    override fun getViewBinding() = ActivityHomeBinding.inflate(layoutInflater)

    override fun initView() {
        initUI()
        initListener()
    }

    private fun initUI() {
        with(binding) {
            storyRV.apply {
                setHasFixedSize(true)
                adapter = adapterStory
            }
        }
    }

    private fun initListener() {
        with(binding) {

            logoutIV.setOnClickListener {
                logout()
            }

            addFAB.setOnClickListener { openActivity(AddStoryActivity::class.java) }

            storyRV.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
                override fun onScrolled(@NonNull recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!storyRV.canScrollVertically(1)) {
                        loadingIndicator.isAreVisible(true)
                        UtilCoroutines.main {
                            viewModel.getStory(
                                ListStoryRequest(
                                    currentPage,
                                    currentSize
                                )
                            )
                        }
                    }
                }
            })
        }
    }

    private fun logout() {
        UtilCoroutines.main {
            dataStore?.clearDataStore()
            showLoading(true)
            delay(2000)
            showLoading(false)
            openActivity(LoginActivity::class.java)
            finish()
        }
    }

    override fun initObservers() {
        viewModel.getStory(ListStoryRequest(currentPage, currentSize))
        viewModel.storyResponse.observe(this) {
            when (it) {
                is DataResource.Loading -> if (currentPage == 1) Log.e("TAG", "LOADING")
                is DataResource.Success -> updateUI(it.value)
                is DataResource.Error -> handleError(it.errorBody)
            }
        }

        viewModel.dataUser.observe(this) {
            binding.nameTV.text = it.name
        }
    }

    private fun updateUI(value: ListStoryResponse) {
        showLoading(false)
        if (value.error == true) {
            myError(value.message ?: "")
            return
        }
        if (value.listStory?.isNotEmpty() == true) currentPage += 1
        binding.loadingIndicator.isAreVisible(false)
        value.listStory?.let { adapterStory.setData(it) }
    }

    override fun onResume() {
        super.onResume()
        currentPage = 1
        adapterStory.clearData()
    }
}