package com.example.charmoaz.ui

import android.Manifest
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.SearchViewBindingAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.hbsis.padawan.posmanagement.ui.cadastrocliente.VerificacoesTextWatcher
import com.example.charmoaz.R
import com.example.charmoaz.data.entity.Cliente
import com.example.charmoaz.databinding.ActivityMainBinding
import com.example.charmoaz.showToast
import kotlinx.android.synthetic.main.content_main.view.*
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest

class MainActivity : AppCompatActivity(), MainAdapter.OnItemAction {

    private val recycler by lazy { binding.includeContentMain.recyclerView }

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()

        EasyPermissions.requestPermissions(
            PermissionRequest.Builder(
                this,
                10,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
                .build()
        )

        viewModel.fetchCliente()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupList()
        viewModel.fetchCliente()

        viewModel.errorMessage.observe(
            this, Observer { this.showToast(it) })

        binding.fab.setOnClickListener(View.OnClickListener {
            val i = Intent(applicationContext, CadastroClienteActivity::class.java)
            startActivity(i)
        })


        binding.includeContentMain.search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                (recycler.adapter as MainAdapter).filter.filter(newText)
                return false
            }

        })
    }

    private fun setupList() {
        recycler.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = MainAdapter(this@MainActivity)
       }

        viewModel.clienteList.observe(this, Observer {
            (recycler.adapter as MainAdapter).uptadeList(it)
        })

    }

    override fun onDelete(cliente: Cliente) {
        viewModel.deletCliente(cliente)
    }

    override fun onDetail(id: Long) {

    }

}
