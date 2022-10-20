package com.aldhykohar.first_submission_intermediate.data.model

import com.google.gson.annotations.SerializedName

data class SuccessResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
