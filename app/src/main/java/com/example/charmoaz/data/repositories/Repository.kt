package br.com.ambev.comodato.callerassinae.data.repositories

import com.example.charmoaz.AppApplication
import com.example.charmoaz.data.dao.ClienteDao
import com.example.charmoaz.data.entity.Cliente
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class Repository {

    private val clienteDao: ClienteDao by lazy { AppApplication.database.clienteDao() }

    fun getAll(): Observable<List<Cliente>> = Observable
        .fromCallable {

            val clientes = clienteDao.findAll()

            clientes
        }
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())


    fun insert(cliente: Cliente): Long {
        val dao = AppApplication.database.clienteDao()

        return if (cliente.id == 0L)
            dao.insert(cliente)
        else {
            dao.update(cliente)
            cliente.id
        }
    }


}
