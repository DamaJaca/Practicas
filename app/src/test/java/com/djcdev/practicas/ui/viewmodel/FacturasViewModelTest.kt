package com.djcdev.practicas.ui.viewmodel

import android.util.Log
import com.djcdev.practicas.domain.Repository
import com.djcdev.practicas.domain.model.FacturaModel
import com.djcdev.practicas.domain.usecase.FilterFacturasUseCase
import com.djcdev.practicas.domain.usecase.GetFacturasUseCase
import com.djcdev.practicas.ui.facturas.FacturasState
import com.djcdev.practicas.ui.facturas.FacturasViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FacturasViewModelTest{


    lateinit var getFacturasUseCase: GetFacturasUseCase
    lateinit var filterFacturasUseCase: FilterFacturasUseCase
    lateinit var facturasViewModel: FacturasViewModel
    @Mock
    lateinit var repository: Repository

    @Before
    fun onBefore(){
        getFacturasUseCase= GetFacturasUseCase(repository)
        filterFacturasUseCase = FilterFacturasUseCase(repository)
        Dispatchers.setMain(TestCoroutineDispatcher())
        facturasViewModel = FacturasViewModel(getFacturasUseCase, filterFacturasUseCase)
    }

    @Test
    fun `when you call get facturas and it resturns a list from api`() = runBlockingTest{
        //arrange
        val facturas = listOf(
            FacturaModel("Pagada", 10.25, "25/10/2024"),
            FacturaModel("Pagada", 10.25, "25/10/2024"),
            FacturaModel("Pagada", 10.25, "25/10/2024"),
            FacturaModel("Pagada", 10.25, "25/10/2024")
        )
        Mockito.`when`(getFacturasUseCase.invoke(false)).thenReturn(facturas)

        //act
        facturasViewModel.getFacturas(false)
        advanceUntilIdle()


        //asserts
        assertEquals(FacturasState.Success(facturas), facturasViewModel.state.value)
    }



    @Test
    fun `when you call get facturas and it resturns an emptyList`() = runBlockingTest{
        //arrange
        Mockito.`when`(getFacturasUseCase.invoke(false)).thenReturn(null)

        //act
        facturasViewModel.getFacturas(false)
        advanceUntilIdle()


        //asserts
        assertEquals(FacturasState.Error("Error al recoger la información (FacturasViewModel)"), facturasViewModel.state.value)
    }


}