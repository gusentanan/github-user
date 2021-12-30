package com.bagusmerta.github_user.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @field:SerializedName("items")
    val items: List<UserItemResponse>?,

    @field:SerializedName("total_count")
    val totalCount: Int?
)
