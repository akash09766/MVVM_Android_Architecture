package com.skylightdevelopers.android.udemystateofartandroidapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.skylightdevelopers.android.udemystateofartandroidapp.di.AppModule
import com.skylightdevelopers.android.udemystateofartandroidapp.di.DaggerViewModelComponent
import com.skylightdevelopers.android.udemystateofartandroidapp.di.CONTEXT_OF_TYPE_APP
import com.skylightdevelopers.android.udemystateofartandroidapp.di.TypeOfContext
import com.skylightdevelopers.android.udemystateofartandroidapp.model.AnimalData
import com.skylightdevelopers.android.udemystateofartandroidapp.model.ApiKey
import com.skylightdevelopers.android.udemystateofartandroidapp.networking.AnimalApiService
import com.skylightdevelopers.android.udemystateofartandroidapp.util.SharedPreferenceHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

const val TAG = "ListViewModel"

class ListViewModel(application: Application) : AndroidViewModel(application) {


    private val _animals = MutableLiveData<List<AnimalData>>()
    val animals: LiveData<List<AnimalData>> = _animals

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val disposable = CompositeDisposable()

    @Inject
    lateinit var apiService: AnimalApiService

    @Inject
    @Singleton
    @field:TypeOfContext(CONTEXT_OF_TYPE_APP)
    lateinit var prefs: SharedPreferenceHelper
    private var injected = false

    constructor(application: Application, test: Boolean = true) : this(Application()) {
        injected = true
    }

    fun inject() {
        if (!injected) {
            DaggerViewModelComponent.builder()
                .appModule(AppModule(getApplication()))
                .build()
                .inject(this)
        }
    }


    fun getData() {
        inject()
        if (_animals.value == null) {// due to this check our ViewModel will not load data again after configuration change.

            _isLoading.value = true

            val key = prefs.getKey()
            if (key.isNullOrEmpty()) {
                getApiKey()
            } else {
                getAnimalData(key)
            }
        }
    }

    fun refresh() {
        inject()
        _isLoading.value = true
        val key = prefs.getKey()
        if (key.isNullOrEmpty()) {
            getApiKey()
        } else {
            getAnimalData(key)
        }
    }

    private fun getApiKey() {

        disposable.add(
            apiService.api.getKey()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ApiKey>() {
                    override fun onSuccess(apiKey: ApiKey) {

                        if (apiKey.key.isNullOrEmpty()) {
                            _isLoading.value = false
                            _isError.value = true
                        } else {
                            prefs.saveKey(apiKey.key)
                            getAnimalData(apiKey.key)
                        }

                    }

                    override fun onError(e: Throwable) {
                        _isLoading.value = false
                        _isError.value = true
                    }

                })
        )

    }

    private fun getAnimalData(apiKey: String?) {

        disposable.addAll(
            apiService.api.getAnimalData(apiKey)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<AnimalData>>() {
                    override fun onSuccess(list: List<AnimalData>) {
                        _isLoading.value = false
                        _isError.value = false
                        _animals.value = list
                    }

                    override fun onError(e: Throwable) {

                        _isLoading.value = false
                        _isError.value = true
                    }

                })
        )

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}