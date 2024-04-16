package com.djcdev.practicas.data.database

import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.djcdev.practicas.data.database.dao.FacturaDao
import com.djcdev.practicas.data.database.entities.FacturaEntity

@Database(entities = [FacturaEntity::class], version = 1)
abstract class FacturasDataBase :RoomDatabase(){

    abstract fun getFacturasDao():FacturaDao

}