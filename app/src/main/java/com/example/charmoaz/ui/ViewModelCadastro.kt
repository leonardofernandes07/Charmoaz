package com.example.charmoaz.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import br.com.ambev.comodato.callerassinae.data.repositories.Repository
import com.example.charmoaz.asImmutable
import com.example.charmoaz.data.entity.Cliente
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.CompletableEmitter
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ViewModelCadastro : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private var _clienteId: Long? = null

    private val _loading: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading = _loading.asImmutable()

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessage = _errorMessage.asImmutable()

    private val _finished: MutableLiveData<Boolean> = MutableLiveData(false)
    val finished = _finished.asImmutable()

    private val _cliente: MutableLiveData<Cliente> = MutableLiveData()
    val cliente = _cliente.asImmutable()

    private val _isEditing: MutableLiveData<Boolean> = MutableLiveData(false)
    val isEditing: LiveData<Boolean> = Transformations.map(_isEditing) {
        _clienteId == null || it
    }

    private val repository: Repository by lazy { Repository() }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun isEditing(){
        _isEditing.postValue(true)
    }

    fun setClientId(id: Long?) {
        _clienteId = id
        _loading.value = true
        id?.let {
            repository.getClienteById(id)
                .doOnNext {
                    _cliente.postValue(it)
                }
                .doOnComplete {
                    _loading.postValue(false)
                }
                .subscribe()
                .also {
                    compositeDisposable.remove(it)
                    compositeDisposable.add(it)
                }
        } ?: _cliente.postValue(Cliente()).also { _loading.value = false }
    }

    fun saveCliente(cliente: Cliente) {
        if(_loading.value == true)
            return

        _loading.value = true

        Completable.create { emmiter: @NonNull CompletableEmitter ->
            try {
                repository.insertOrUpdate(cliente)
                emmiter.onComplete()
            } catch (e: Exception) {
                emmiter.onError(e)
            }
        }.subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                _finished.postValue(true)
            }
            .doOnError {
                _errorMessage.postValue(it.message)
                _loading.postValue(false)
            }
            .subscribe()
    }
}

