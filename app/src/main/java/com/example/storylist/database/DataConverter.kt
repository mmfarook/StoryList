package com.example.storylist.database

import androidx.room.TypeConverter
import com.example.storylist.model.Story
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson


class DataConverter {

    @TypeConverter
    fun fromStorytoString(story: Story?): String? {
        if (story == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<Story>() {
        }.type
        return gson.toJson(story, type)
    }

    @TypeConverter
    fun fromStringtoStory(storyString: String?): Story? {
        if (storyString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<Story>() {

        }.type
        return gson.fromJson(storyString, type)
    }
}