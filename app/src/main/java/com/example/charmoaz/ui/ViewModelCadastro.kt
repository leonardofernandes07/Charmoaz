package com.example.charmoaz.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.ambev.comodato.callerassinae.data.repositories.Repository
import com.example.charmoaz.asImmutable
import com.example.charmoaz.data.entity.Cliente
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.CompletableEmitter
import io.reactivex.rxjava3.core.CompletableOnSubscribe
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ViewModelCadastro : ViewModel(){



    private val compositeDisposable = CompositeDisposable()

    private val _loading: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading = _loading.asImmutable()

    private val _clienteList: MutableLiveData<List<Cliente>> = MutableLiveData(emptyList())
    val clienteList = _clienteList.asImmutable()

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessage = _errorMessage.asImmutable()

    private val _cliente: MutableLiveData<Cliente> = MutableLiveData()
    val cliente = _cliente.asImmutable()

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

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun saveCliente(cliente: Cliente) {
        _loading.value = true

        Completable.create(CompletableOnSubscribe { emmiter: @NonNull CompletableEmitter ->
            try {
                repository.insert(cliente)
                emmiter.onComplete()
            } catch (e: Exception) {
                emmiter.onError(e)
            }
        }).subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete() {
                fetchCliente()
            }
            .doOnError {
                _errorMessage.postValue(toString())
            }
            .subscribe()
    }

    fun selectById(id : Long): MutableLiveData<Cliente> {
        repository.getClienteById(id)
            .doOnNext {
                _cliente.postValue(it)
            }
            .doOnComplete {
                _loading.postValue(false)
            }
            .subscribe().also {
                compositeDisposable.remove(it)
                compositeDisposable.add(it)
            }
        return _cliente
    }
}

