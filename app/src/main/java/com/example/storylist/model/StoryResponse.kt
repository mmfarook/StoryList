package com.example.storylist.model


import com.google.gson.annotations.SerializedName

data class StoryResponse(
    @SerializedName("slug")
    val slug: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("summary")
    val summary: String? = null,

    @SerializedName("items")
    val items: List<StoryItems>? = null

)