package com.djcdev.practicas.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.djcdev.practicas.domain.model.FacturaModel


@Entity(tableName= "factura_table")
data class FacturaEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id") val id:Int=0,
    @ColumnInfo("descEstado") val estado:String,
    @ColumnInfo("importeOrdenacion") val importe:Double,
    @ColumnInfo("fecha") val fecha:String
)
{
    fun toDomain():FacturaModel{
        return FacturaModel(estado,importe,fecha)
    }
}