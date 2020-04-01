package com.example.charmoaz.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.charmoaz.R
import com.example.charmoaz.data.entity.Cliente
import com.example.charmoaz.databinding.ItemListaClienteBinding


class MainAdapter(private val listener: MainActivity) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private val viewModel by lazy { MainViewModel() }

    private val listCliente = mutableListOf<Cliente>()

    inner class ViewHolder(private val binding: ItemListaClienteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cliente: Cliente) {
            binding.cliente = cliente

            binding.delet.setOnClickListener(View.OnClickListener {
                viewModel.deletCliente(listCliente[adapterPosition])
                if (viewModel.loading.value == true){
                }else{
                    uptadeList(listCliente)
                }
            })
        }
    }

    fun uptadeList(list: List<Cliente>) {
        listCliente.clear()
        listCliente.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemListaClienteBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_lista_cliente,
            parent,
            false
        )
        binding.cardView.setOnClickListener(View.OnClickListener {

        })
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listCliente.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listCliente[position])
    }


}
