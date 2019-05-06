package com.example.storylist.repository

import android.util.Log
import com.example.storylist.StoryApplication
import com.example.storylist.database.StoryItemsDao
import com.example.storylist.model.CollectionStories
import com.example.storylist.model.StoryItems
import com.example.storylist.service.StoryService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import java.util.*
import javax.inject.Inject


class StoryRepository @Inject
constructor(private val retrofit: Retrofit, private val storyItemsDao: StoryItemsDao) {
    private val TAG: String = "StoryRepository"
    val stories: Observable<List<StoryItems>>
        get() {
            if (StoryApplication.shouldFromServer()) {
                val storyService = retrofit.create(StoryService::class.java)
                val storyMap = LinkedHashMap<String, ArrayList<StoryItems>>()
                val storyListIds = ArrayList<String>()

                return storyService.getAllStories("collection")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap { (_, _, _, itemsList) ->
                        val observableList = ArrayList<Observable<CollectionStories>>()
                        for (storyItems in itemsList!!) {
                            val list = ArrayList<StoryItems>()
                            list.add(storyItems)

                            storyItems.subType = storyItems.type
                            if (ItemType.COLLECTION.toString() == storyItems.type) {
                                //storyMap.put(storyItems.getId(), list);
                                storyMap[storyItems.slug!!] = list
                                storyListIds.add(storyItems.slug!!)
                                observableList.add(getStoryObservable(storyItems))
                            } else {
                                storyMap[storyItems.story!!.slug!!] = list
                            }
                        }
                        Observable.zip(
                            observableList
                        ) { objects ->
                            val collectionStories = ArrayList<CollectionStories>()
                            for (i in objects.indices) {
                                val c = objects[i] as CollectionStories
                                collectionStories.add(c)
                                val list: ArrayList<StoryItems>? = storyMap[storyListIds[i]]
                                val storyItemsForCollection = c.items
                                var isSubType = false
                                for (storyItem in storyItemsForCollection!!) {
                                    if (!isSubType) {
                                        storyItem.subType = storyItem.type
                                        isSubType = true
                                    } else {
                                        storyItem.subType = ItemType.COLLECTION_SUB_STORY.toString()
                                    }
                                }
                                list!!.addAll(c.items!!)
                                storyMap[storyListIds[i]] = list
                            }
                            val storyItems = ArrayList<StoryItems>()
                            for ((_, value) in storyMap) {
                                storyItems.addAll(value)
                            }

                            //Cache the result, so that every time you will load data from the DB, and
                            // refresh your DB from API once a day
                            // fetch from local DB
                            storyItemsDao.deleteAll()
                            storyItemsDao.insertAll(storyItems)
                            StoryApplication.storePreference(
                                StoryApplication.SERVER_FETCH_TIME,
                                System.currentTimeMillis()
                            )
                            Log.e(TAG, "Loaded data from server")
                            storyItems
                        }.subscribeOn(Schedulers.io())
                    }
            } else {
                Log.e(TAG, "Loaded data from local DB")
                // use observable to avoid db operation in main thread
                return Observable.just(storyItemsDao)
                    .subscribeOn(Schedulers.io())
                    .flatMap { Observable.just(storyItemsDao.getStories()) }

            }
        }

    private fun getStoryObservable(storyItems: StoryItems): Observable<CollectionStories> {
        val storyService = retrofit.create(StoryService::class.java)
        return storyService.getStories(storyItems.slug!!)
    }
}
