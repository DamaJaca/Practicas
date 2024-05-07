package com.djcdev.practicas.ui.facturas.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.djcdev.practicas.R
import com.djcdev.practicas.domain.model.FacturaModel

class FacturasAdapter (private var facturasList :List<FacturaModel> = emptyList(), private val inSelected:()->Unit):RecyclerView.Adapter<FacturasViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacturasViewHolder {
        return FacturasViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_factura, parent, false)
        )
    }

    override fun getItemCount(): Int = facturasList.size

    override fun onBindViewHolder(holder: FacturasViewHolder, position: Int) {
        holder.render (facturasList[position])
        holder.itemView.setOnClickListener { inSelected () }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(lista :List<FacturaModel>){
        facturasList = lista
        notifyDataSetChanged()
    }

    fun getMaxImport(): Float {

        return if (facturasList.isNotEmpty()) facturasList.maxOf { it.importe }.toFloat() else 120.0f

    }


}