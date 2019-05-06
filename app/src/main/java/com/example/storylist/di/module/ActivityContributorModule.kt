package com.example.storylist.di.module

import com.example.storylist.di.scopes.ActivityScoped
import com.example.storylist.ui.detail.DetailActivity
import com.example.storylist.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityContributorModule {

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun contributeMainActivity(): MainActivity

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun contributeDetailActivity(): DetailActivity
}