package com.aldhykohar.first_submission_intermediate.base

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import com.aldhykohar.first_submission_intermediate.data.model.SuccessResponse
import com.aldhykohar.first_submission_intermediate.data.network.DataResource
import com.aldhykohar.first_submission_intermediate.utils.CustomDialogBar
import com.aldhykohar.first_submission_intermediate.utils.UtilExtensions.myError
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody


/**
 * Created by aldhykohar on 3/12/2022.
 */
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    lateinit var binding: VB
    private var progressDialog: CustomDialogBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        progressDialog = CustomDialogBar()
        setContentView(binding.root)
        initView()
        initObservers()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    abstract fun getViewBinding(): VB

    abstract fun initView()

    abstract fun initObservers()

    protected fun initToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    protected fun handleError(failure: ResponseBody?) {
        try {
            val gson = Gson()
            val type = object : TypeToken<SuccessResponse>() {}.type
            val errorResponse: SuccessResponse? =
                gson.fromJson(failure?.charStream(), type)
            myError(errorResponse?.message ?: "")
        } catch (e: Exception) {
            myError(e.message ?: "")
            e.printStackTrace()
        }
        showLoading(false)
    }

    protected fun showLoading(isShown: Boolean) {
        if (isShown) progressDialog?.showProgress(this)
        else progressDialog?.hide()
    }

}