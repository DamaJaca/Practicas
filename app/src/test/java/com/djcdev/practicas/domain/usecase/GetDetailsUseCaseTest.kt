package com.djcdev.practicas.domain.usecase

import com.djcdev.practicas.domain.Repository
import com.djcdev.practicas.domain.model.DetailModel
import com.djcdev.practicas.domain.model.FacturaModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetDetailsUseCaseTest{
    private lateinit var repository: Repository
    private lateinit var getDetailsUseCase: GetDetailsUseCase
    private lateinit var detail: DetailModel

    @Before
    fun onBefore (){
        repository = Mockito.mock(Repository::class.java)
        getDetailsUseCase = GetDetailsUseCase(repository)
        detail = DetailModel("cau","estado", "tipo","comp","potencia")
    }

    @Test
    fun `when we you get a list`() = runBlocking {

        //arrange
        Mockito.`when`(repository.getDetails()).thenReturn(detail)
        val expected = detail

        //act
        val actual = getDetailsUseCase.invoke()

        //assert
        assertEquals(expected, actual)
    }

    @Test
    fun `when we you get null`() = runBlocking {

        //arrange
        Mockito.`when`(repository.getDetails()).thenReturn(null)
        val expected = null

        //act
        val actual = getDetailsUseCase.invoke()

        //assert
        assertEquals(expected, actual)
    }

}