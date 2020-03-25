package com.example.charmoaz.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.ambev.comodato.callerassinae.data.repositories.Repository
import com.example.charmoaz.asImmutable
import com.example.charmoaz.data.entity.Cliente
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel : ViewModel(){

    private val compositeDisposable = CompositeDisposable()

    private val _loading: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading = _loading.asImmutable()

    private val _clienteList: MutableLiveData<List<Cliente>> = MutableLiveData(emptyList())
    val clienteList = _clienteList.asImmutable()

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessage = _errorMessage.asImmutable()

    private val repository: Repository by lazy { Repository() }

    init {
        fetchCliente()
    }

    fun fetchCliente() {
        _loading.value = true

        repository.getAll()
            .doOnNext {
                _clienteList.postValue(it)
            }.doOnError {
                _loading.value = false
                _errorMessage.postValue(it.message)
            }
            .doOnComplete {
                _loading.postValue(false)

            }
            .subscribe().also {
                compositeDisposable.remove(it)
                compositeDisposable.add(it)
            }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}
