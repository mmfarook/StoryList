package com.example.storylist.model

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class Story(
    @SerializedName("author-name")
    val authorName: String? = null,

    @SerializedName("headline")
    val headline: String? = null,

    @SerializedName("slug")
    @ColumnInfo(name= "story_slug")
    val slug: String? = null,

    @SerializedName("summary")
    val summary: String? = null,

    @SerializedName("hero-image")
    val heroImage: String? = null
)