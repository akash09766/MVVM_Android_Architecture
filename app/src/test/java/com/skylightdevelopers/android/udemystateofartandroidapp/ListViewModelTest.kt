package com.skylightdevelopers.android.udemystateofartandroidapp

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.skylightdevelopers.android.udemystateofartandroidapp.di.AppModule
import com.skylightdevelopers.android.udemystateofartandroidapp.di.DaggerViewModelComponent
import com.skylightdevelopers.android.udemystateofartandroidapp.model.AnimalData
import com.skylightdevelopers.android.udemystateofartandroidapp.networking.AnimalApiService
import com.skylightdevelopers.android.udemystateofartandroidapp.util.SharedPreferenceHelper
import com.skylightdevelopers.android.udemystateofartandroidapp.viewModel.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor

class ListViewModelTest {


    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var animalApiService: AnimalApiService
    @Mock
    lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    val application = Mockito.mock(Application::class.java)
    val listViewModel = ListViewModel(application)

    private val key = "test key"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        DaggerViewModelComponent.builder()
            .appModule(AppModule(application))
            .apiModule(ApiModuleTest(animalApiService))
            .prefsModule(PrefsModuleTest(sharedPreferenceHelper))
            .build()
            .inject(listViewModel)
    }

    @Test
    open fun getAnimalSuccess() {
        Mockito.`when`(sharedPreferenceHelper.getKey()).thenReturn(key)
        val animalData = AnimalData(null, null, null, null, "Tiger", null, null)
        val listAnimal = listOf(animalData)

        val testSingle = Single.just(listAnimal)

        Mockito.`when`(animalApiService.getAnimals(key)).thenReturn(testSingle)

        listViewModel.getData()

        Assert.assertEquals(1, listViewModel.animals.value?.size)
    }

    @Before
    fun setupRxSchedulers() {
        val immediate = object : Scheduler() {
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() }, true)
            }
        }

        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate }
    }
}