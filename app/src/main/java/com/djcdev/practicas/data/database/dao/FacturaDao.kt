package com.djcdev.practicas.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.djcdev.practicas.data.database.entities.FacturaEntity

@Dao
interface FacturaDao {

    @Query("Select * From factura_table")
    suspend fun getAllFacturas():List<FacturaEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(facturas:List<FacturaEntity>)

    @Query("Delete from factura_table")
    suspend fun deleteAll()

}