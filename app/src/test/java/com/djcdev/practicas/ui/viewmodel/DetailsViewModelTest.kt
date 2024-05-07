package com.djcdev.practicas.ui.viewmodel

import com.djcdev.practicas.domain.Repository
import com.djcdev.practicas.domain.model.DetailModel
import com.djcdev.practicas.domain.usecase.GetDetailsUseCase
import com.djcdev.practicas.ui.smartsolar.pagesfromtabs.DetailsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailsViewModelTest{

    @Mock
    lateinit var repository: Repository
    lateinit var getDetailsUseCase: GetDetailsUseCase
    lateinit var detailsViewModel: DetailsViewModel

    val details = DetailModel("cau", "estado","tipo","compern","potencia")

    @Before
    fun onBefore(){
        getDetailsUseCase= GetDetailsUseCase(repository)
        detailsViewModel= DetailsViewModel(getDetailsUseCase)
    }

    @get:Rule
    val mainDispatcherRule=MainDispatcherRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when you get details and its not null`()= runTest{
        //arrange
        Mockito.`when`(getDetailsUseCase()).thenReturn(details)

        //act
        detailsViewModel.getDetails()
        advanceUntilIdle()

        //asserts
        assertEquals(details, detailsViewModel.details.value)
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when you get details and its null`()= runTest{
        //arrange
        Mockito.`when`(getDetailsUseCase()).thenReturn(null)

        //act
        detailsViewModel.getDetails()
        advanceUntilIdle()

        //asserts
        assertEquals(DetailModel("Error de Carga","", "","","" ), detailsViewModel.details.value)
    }


}