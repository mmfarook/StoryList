package com.example.storylist.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storylist.R
import com.example.storylist.adapter.StoryAdapter
import com.example.storylist.model.StoryItems
import com.example.storylist.ui.detail.DetailActivity
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), StoryAdapter.ItemClickListener {

    val TAG: String = "MainActivity"

    protected lateinit var mainViewModel: MainViewModel

    @Inject
    protected lateinit var mainViewModelFactory: MainViewModelFactory

    private val mSubscription = CompositeDisposable()

    private lateinit var mStoryAdapter: StoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProviders.of(this, mainViewModelFactory).get(MainViewModel::class.java)
        mStoryAdapter = StoryAdapter(this)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = mStoryAdapter
        bindViewModel()
    }

    private fun bindViewModel() {
        mSubscription.clear()
        progress_bar.setVisibility(View.VISIBLE)
        mSubscription.add(
            mainViewModel.stories
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { stories: List<StoryItems> ->
                        mStoryAdapter.submitList(stories)
                        progress_bar.setVisibility(View.GONE)

                    },
                    { e: Throwable ->
                        progress_bar.setVisibility(View.GONE)
                        Log.e(TAG, e.toString())
                    }
                ))

    }

    public override fun onDestroy() {
        super.onDestroy()
        unbindViewModel()
    }

    private fun unbindViewModel() {
        mSubscription.dispose()
        mSubscription.clear()
    }

    override fun onItemClick(storyItem: StoryItems, view: View) {

        val intent = Intent(this, DetailActivity::class.java)
        mainViewModel.setSelectedItem(storyItem)
        var name: String? = ViewCompat.getTransitionName(view)
        if (name.isNullOrEmpty()) {
            startActivity(intent)
        } else {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                view,
                name
            )
            startActivity(intent, options.toBundle())
        }


    }
}
