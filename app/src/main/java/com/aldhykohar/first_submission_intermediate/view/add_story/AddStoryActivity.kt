package com.aldhykohar.first_submission_intermediate.view.add_story

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.aldhykohar.first_submission_intermediate.R
import com.aldhykohar.first_submission_intermediate.base.BaseActivity
import com.aldhykohar.first_submission_intermediate.data.model.SuccessResponse
import com.aldhykohar.first_submission_intermediate.data.network.DataResource
import com.aldhykohar.first_submission_intermediate.databinding.ActivityAddStoryBinding
import com.aldhykohar.first_submission_intermediate.databinding.DialogUploadImageBinding
import com.aldhykohar.first_submission_intermediate.utils.UtilConstants.REQUEST_CODE_PERMISSIONS
import com.aldhykohar.first_submission_intermediate.utils.UtilConstants.REQUIRED_PERMISSIONS
import com.aldhykohar.first_submission_intermediate.utils.UtilExtensions.allPermissionsGranted
import com.aldhykohar.first_submission_intermediate.utils.UtilExtensions.createTempFile
import com.aldhykohar.first_submission_intermediate.utils.UtilExtensions.myError
import com.aldhykohar.first_submission_intermediate.utils.UtilExtensions.myToast
import com.aldhykohar.first_submission_intermediate.utils.UtilExtensions.reduceFileImage
import com.aldhykohar.first_submission_intermediate.utils.UtilExtensions.uriToFile
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class AddStoryActivity : BaseActivity<ActivityAddStoryBinding>() {

    private val viewModel by viewModels<AddStoryViewModel>()

    private lateinit var currentPhotoPath: String

    var image: MultipartBody.Part? = null


    override fun getViewBinding() = ActivityAddStoryBinding.inflate(layoutInflater)

    override fun initView() {
        prepare()
        initClick()
    }

    private fun initClick() {
        with(binding) {

            rl1.setOnClickListener {
                if (!allPermissionsGranted()) {
                    ActivityCompat.requestPermissions(
                        this@AddStoryActivity,
                        REQUIRED_PERMISSIONS,
                        REQUEST_CODE_PERMISSIONS
                    )
                    return@setOnClickListener
                }
                openDialogUploadImage()
            }
            addMB.setOnClickListener {
                val desc = descriptionET.text.toString()
                if (desc.isEmpty()) {
                    descriptionET.error = getString(R.string.field_cannot_blank)
                } else if (image == null) {
                    myToast(getString(R.string.please_upload_image))
                } else {
                    viewModel.addStory(image!!, desc)
                }
            }
        }
    }

    private fun prepare() {
        initToolbar(binding.appBar.toolbar)
        binding.appBar.titleBarTV.text = getString(R.string.add_story)
    }

    override fun initObservers() {
        viewModel.storyResponse.observe(this) {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> updateUI(it.value)
                is DataResource.Error -> handleError(it.errorBody)
            }
        }
    }

    private fun updateUI(value: SuccessResponse) {
        showLoading(false)
        if (value.error == true) {
            myError(value.message ?: "")
            return
        }
        myToast(value.message ?: "")
        finish()
    }

    fun openDialogUploadImage() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        val binding = DialogUploadImageBinding.inflate(layoutInflater)
        alertDialogBuilder.setView(binding.root)
        val dialog = alertDialogBuilder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.cameraTV.setOnClickListener {
            startTakePhoto()
            dialog.cancel()
        }
        binding.galleryTV.setOnClickListener {
            startGallery()
            dialog.cancel()
        }

        dialog.show()
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)
        createTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this,
                "com.aldhykohar.first_submission_intermediate",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            setImageMultipart(myFile)
            val result = BitmapFactory.decodeFile(myFile.path)

            binding.imageIV.setImageBitmap(result)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } else {
                openDialogUploadImage()
            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this)
            setImageMultipart(myFile)
            binding.imageIV.setImageURI(selectedImg)
        }
    }

    private fun setImageMultipart(file: File) {
        val files = reduceFileImage(file)
        val body = RequestBody.create(
            "image/*".toMediaTypeOrNull(),
            files
        )
        image = MultipartBody.Part.createFormData("photo", file.name, body)
    }

}