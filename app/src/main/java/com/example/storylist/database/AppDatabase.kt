package com.example.storylist.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.storylist.model.StoryItems

@Database(entities = [StoryItems::class], exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun storyItemsDao(): StoryItemsDao
}