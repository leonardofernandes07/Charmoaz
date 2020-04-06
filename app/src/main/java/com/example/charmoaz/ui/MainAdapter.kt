package com.example.charmoaz.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.charmoaz.R
import com.example.charmoaz.data.entity.Cliente
import com.example.charmoaz.databinding.ItemListaClienteBinding


class MainAdapter(private val listener: OnItemAction) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>(), Filterable {

    interface OnItemAction {
        fun onDelete(cliente: Cliente)
        fun onDetail(id: Long)
    }

    private val listCliente = mutableListOf<Cliente>()
    private val listClienteFull = mutableListOf<Cliente>()

    inner class ViewHolder(private val binding: ItemListaClienteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cliente: Cliente) {
            binding.cliente = cliente
            binding.root.setOnClickListener {

            }
            binding.delet.setOnClickListener(View.OnClickListener {
                listener.onDelete(listCliente[adapterPosition])
            })
        }
    }

    fun uptadeList(list: List<Cliente>) {
        listClienteFull.apply {
            clear()
            addAll(list)
        }

        updateFilter(listClienteFull)

        notifyDataSetChanged()
    }

    private fun updateFilter(list: List<Cliente>){
        listCliente.apply {
            clear()
            addAll(list)
        }
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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                var filteredList = mutableListOf<Cliente>()

                if (constraint.isEmpty()){
                    filteredList.addAll(listClienteFull)
                }else{
                    val filterPatter = constraint.toString().toLowerCase().trim()

                    listClienteFull.forEach{
                        if(it.clienteNome.toLowerCase().contains(filterPatter)){
                            filteredList.add(it)
                        }
                    }
                }

                val results = FilterResults()
                results.values = filteredList

                return results
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults) {
                updateFilter(results.values as List<Cliente>)
                notifyDataSetChanged()
            }
        }

    }
}
