package com.skylightdevelopers.android.udemystateofartandroidapp.di

import com.skylightdevelopers.android.udemystateofartandroidapp.viewModel.ListViewModel
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ApiModule::class,PrefsModule::class,AppModule::class])
@Singleton
interface ViewModelComponent {
    fun inject(listViewModel: ListViewModel)
}