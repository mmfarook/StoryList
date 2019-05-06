package com.example.storylist.database

import androidx.room.*
import com.example.storylist.model.StoryItems
import javax.inject.Singleton


@Singleton
@Dao
interface StoryItemsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(storyItem: StoryItems)

    @Update
    fun update(storyItem: StoryItems)

    @Delete
    fun delete(storyItem: StoryItems)

    @Query("SELECT * FROM StoryItems")
    fun getStories(): List<StoryItems>

    @Query("DELETE FROM StoryItems")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertAll(storyItems: List<StoryItems>)
}