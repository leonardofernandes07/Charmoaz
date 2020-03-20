package com.example.charmoaz.ui

import androidx.lifecycle.MutableLiveData
import br.com.ambev.comodato.callerassinae.data.repositories.Repository
import com.example.charmoaz.asImmutable
import com.example.charmoaz.data.entity.Cliente
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MainViewModel {

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

    private fun fetchCliente() {
        _loading.value = true

        repository.getAll()
            .doOnNext {
                _clienteList.postValue(it)
            }
            .doOnComplete {
                _loading.postValue(false)

            }
            .subscribe().also {
                compositeDisposable.remove(it)
                compositeDisposable.add(it)
            }
    }
}
