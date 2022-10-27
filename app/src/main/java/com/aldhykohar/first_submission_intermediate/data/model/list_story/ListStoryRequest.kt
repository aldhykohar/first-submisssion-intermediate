package com.aldhykohar.first_submission_intermediate.data.model.list_story

import com.google.gson.annotations.SerializedName

data class ListStoryRequest(

	@field:SerializedName("page")
	val page: Int,

	@field:SerializedName("size")
	val size: Int,
)
