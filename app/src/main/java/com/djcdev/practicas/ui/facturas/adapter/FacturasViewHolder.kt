package com.djcdev.practicas.ui.facturas.adapter

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.djcdev.practicas.databinding.ItemFacturaBinding
import com.djcdev.practicas.domain.model.FacturaModel
import java.text.DecimalFormat

class FacturasViewHolder (view : View):RecyclerView.ViewHolder (view) {

    private val binding = ItemFacturaBinding.bind(view)

    fun render(factura : FacturaModel) {
        binding.tvFechaFactura.text= factura.fecha
        if (factura.estado == "Pendiente de pago"){
            binding.tvPendientePago.isVisible=true
        }

        binding.tvPrecioFactura.text="${doubleToFloat(factura.importe)} €"
    }

    private fun doubleToFloat(importe: Double): Float {
        val formato = DecimalFormat("#.##")
        formato.maximumFractionDigits = 2
        return formato.format(importe).toFloat()

    }
}