@file:OptIn(ExperimentalCoroutinesApi::class)

package com.djcdev.practicas.ui.viewmodel

import com.djcdev.practicas.domain.Repository
import com.djcdev.practicas.domain.model.FacturaModel
import com.djcdev.practicas.domain.usecase.FilterFacturasUseCase
import com.djcdev.practicas.domain.usecase.GetFacturasUseCase
import com.djcdev.practicas.ui.facturas.FacturasState
import com.djcdev.practicas.ui.facturas.FacturasViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FacturasViewModelTest{

    val facturas = listOf(
        FacturaModel("Pagada", 10.25, "25/10/2024"),
        FacturaModel("Pagada", 10.25, "25/10/2024"),
        FacturaModel("Pagada", 10.25, "25/10/2024"),
        FacturaModel("Pagada", 10.25, "25/10/2024")
    )


    lateinit var getFacturasUseCase: GetFacturasUseCase
    lateinit var filterFacturasUseCase: FilterFacturasUseCase
    lateinit var facturasViewModel: FacturasViewModel
    @Mock
    lateinit var repository: Repository

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun onBefore(){
        getFacturasUseCase= GetFacturasUseCase(repository)
        filterFacturasUseCase = FilterFacturasUseCase(repository)
        facturasViewModel = FacturasViewModel(getFacturasUseCase, filterFacturasUseCase)

    }

    @Test
    fun `when you call get facturas and it resturns a list from api`() = runTest{
        //arrange
        Mockito.`when`(getFacturasUseCase.invoke(false)).thenReturn(facturas)

        //act
        run{facturasViewModel.getFacturas(false)}
        advanceUntilIdle()


        //asserts
        assertEquals(FacturasState.Success(facturas), facturasViewModel.state.value)
    }




    @Test
    fun `when you call get facturas and it resturns an emptyList`() = runTest{
        //arrange
        Mockito.`when`(getFacturasUseCase.invoke(false)).thenReturn(null)

        //act
        facturasViewModel.getFacturas(false)
        advanceUntilIdle()


        //asserts
        assertEquals(FacturasState.Error("Error al recoger la información (FacturasViewModel)"), facturasViewModel.state.value)
    }

    @Test
    fun `when you cal filter facturas so you get a list()`() = runTest{
        //arrange
        Mockito.`when`(getFacturasUseCase.invoke(false)).thenReturn(facturas)

        //act
        facturasViewModel.filterFacturas(null, null, null, null, null)
        advanceUntilIdle()

        val actual = facturasViewModel.state.value

        //asserts
        assertEquals(FacturasState.Success(facturas), actual)

    }

    @Test
    fun `when you cal filter facturas so you get an emptyList()`() = runTest(){
        //arrange
        Mockito.`when`(getFacturasUseCase.invoke(false)).thenReturn(facturas)

        //act
        facturasViewModel.filterFacturas(null, null, null, "20/10/2024", "19/10/2024")
        advanceUntilIdle()

        val actual = facturasViewModel.state.value

        //asserts
        assertEquals(FacturasState.Success(emptyList()), actual)

    }


}