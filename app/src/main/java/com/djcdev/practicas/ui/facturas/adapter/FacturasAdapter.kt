package com.djcdev.practicas.ui.facturas.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.djcdev.practicas.R
import com.djcdev.practicas.domain.model.FacturaModel
import com.djcdev.practicas.domain.model.FacturasModel

class FacturasAdapter (private var facturasList :List<FacturaModel> = emptyList()):RecyclerView.Adapter<FacturasViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacturasViewHolder {
        return FacturasViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_factura, parent, false)
        )
    }

    override fun getItemCount(): Int = facturasList.size

    override fun onBindViewHolder(holder: FacturasViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    fun updateList(lista :List<FacturaModel>){

        notifyDataSetChanged()
    }

}