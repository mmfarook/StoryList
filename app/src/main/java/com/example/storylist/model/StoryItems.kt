package com.example.storylist.model

import androidx.room.*
import com.example.storylist.database.DataConverter
import com.google.gson.annotations.SerializedName

@Entity(tableName = "storyitems")
data class StoryItems(
    @PrimaryKey(autoGenerate = true)
    var auto_id: Int,

    @SerializedName("id")
    var id: String? = null,

    @SerializedName("type")
    var type: String? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("url")
    var url: String? = null,

    @SerializedName("slug")
    var slug: String? = null,

    @Embedded
    @TypeConverters(DataConverter::class)
    @SerializedName("story")
    var story: Story? = null,

    var subType: String? = null
) {
    constructor() : this(0, "", "", "", "", "")
}