package com.example.storylist.model

import com.google.gson.annotations.SerializedName

data class CollectionStories(
    @SerializedName("name")
    val name: String? = null,

    @SerializedName("summary")
    val summary: String? = null,

    @SerializedName("items")
    var items: List<StoryItems>? = null
)