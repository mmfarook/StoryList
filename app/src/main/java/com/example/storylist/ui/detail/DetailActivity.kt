package com.example.storylist.ui.detail


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.storylist.R
import com.example.storylist.ui.main.MainViewModel
import com.example.storylist.ui.main.MainViewModelFactory
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject

class DetailActivity : DaggerAppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    @Inject
    protected lateinit var mainViewModelFactory: MainViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        ActivityCompat.postponeEnterTransition(this)
        mainViewModel = ViewModelProviders.of(this, mainViewModelFactory).get(MainViewModel::class.java)
        mainViewModel.selectedPosition.observe(this, Observer {
            it?.let {
                supportActionBar?.setTitle(it.story?.headline)
                ViewCompat.setTransitionName(header_image, it.id)
                headline.text = it.story?.headline
                author_name.text = it.story?.authorName
                summary.text = it.story?.summary
                Picasso.get().load(it.story?.heroImage).placeholder(R.drawable.placeholder_image).into(header_image)
            }
            ActivityCompat.startPostponedEnterTransition(this)
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        ActivityCompat.finishAfterTransition(this)
        return true
    }

}
