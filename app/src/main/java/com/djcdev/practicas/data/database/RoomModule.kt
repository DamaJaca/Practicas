package com.djcdev.practicas.data.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val FACTURAS_DATABASE_NAME ="facturas_database"

    @Singleton
    @Provides
    fun provideRoom (@ApplicationContext context: Context)= Room.databaseBuilder(context, FacturasDataBase::class.java, FACTURAS_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideFacturaDao (db:FacturasDataBase) = db.getFacturasDao()
}