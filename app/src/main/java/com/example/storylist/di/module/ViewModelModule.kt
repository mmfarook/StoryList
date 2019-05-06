package com.example.storylist.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.storylist.ui.main.MainViewModel
import com.example.storylist.ui.main.MainViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMessageViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @Singleton
    internal abstract fun bindViewModelFactory(factory: MainViewModelFactory): ViewModelProvider.Factory
}