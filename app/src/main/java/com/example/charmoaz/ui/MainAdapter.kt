package com.example.charmoaz.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.charmoaz.R
import com.example.charmoaz.data.entity.Cliente
import com.example.charmoaz.databinding.ItemListaClienteBinding


class MainAdapter(private val listener: OnClick) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    interface OnClick {

        fun onClienteClick(cliente: Cliente)

    }

    private val listPdv = mutableListOf<Cliente>()

    inner class ViewHolder(private val binding: ItemListaClienteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cliente: Cliente) {
            binding.cliente = cliente

            binding.cardView.setOnClickListener {
                listener.onClienteClick(cliente)
            }
        }
    }

    fun uptadeList(list: List<Cliente>) {
        listPdv.clear()
        listPdv.addAll(list)
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
            binding.imageView.isVisible = !binding.imageView.isVisible
        })
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listPdv.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listPdv[position])
    }


}
