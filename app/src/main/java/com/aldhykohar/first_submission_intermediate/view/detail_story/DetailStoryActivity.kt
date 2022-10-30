package com.aldhykohar.first_submission_intermediate.view.detail_story

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aldhykohar.first_submission_intermediate.R
import com.aldhykohar.first_submission_intermediate.base.BaseActivity
import com.aldhykohar.first_submission_intermediate.data.model.list_story.ListStoryItem
import com.aldhykohar.first_submission_intermediate.databinding.ActivityDetailStoryBinding
import com.aldhykohar.first_submission_intermediate.utils.UtilConstants.DETAIL
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_story.*

class DetailStoryActivity : BaseActivity<ActivityDetailStoryBinding>() {
    override fun getViewBinding() = ActivityDetailStoryBinding.inflate(layoutInflater)

    override fun initView() {
        initUI()
        initToolbar(binding.appBar.toolbar)
        binding.appBar.titleBarTV.text = getString(R.string.detail_story)
    }

    private fun initUI() {
        val detail: ListStoryItem? = intent.getParcelableExtra(DETAIL)
        detail?.let {
            Picasso.get().load(it.photoUrl).into(binding.imageIV)
            binding.nameTV.text = it.name
            binding.descriptionTV.text = it.description
        }
    }

    override fun initObservers() {

    }
}