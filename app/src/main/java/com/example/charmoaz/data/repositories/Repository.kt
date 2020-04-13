package br.com.ambev.comodato.callerassinae.data.repositories

import com.example.charmoaz.AppApplication
import com.example.charmoaz.data.DatabaseApplication
import com.example.charmoaz.data.dao.ClienteDao
import com.example.charmoaz.data.entity.Cliente
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
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

    fun getClienteById(id : Long): Observable<Cliente> = Observable
        .fromCallable {

            val cliente: Cliente = clienteDao.findById(id)

            cliente
        }
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())



    fun insertOrUpdate(cliente: Cliente): Long {
        val dao = AppApplication.database.clienteDao()
        return if (cliente.id == 0L)
            dao.insert(cliente)
        else {
            dao.update(cliente)
            cliente.id
        }
    }

    fun delete(cliente: Cliente){
        val dao = AppApplication.database.clienteDao()
        dao.delete(cliente)
    }

}
