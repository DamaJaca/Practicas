package com.djcdev.practicas.domain.usecase


import com.djcdev.practicas.domain.Repository
import com.djcdev.practicas.domain.model.FacturaModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class GetFacturasUseCaseTest{

    private lateinit var repository:Repository
    private lateinit var getFacturasUseCase:GetFacturasUseCase
    private lateinit var facturas:List<FacturaModel>
    @Before
    fun onBefore(){
        repository= Mockito.mock(Repository::class.java)
        getFacturasUseCase= GetFacturasUseCase(repository)
        facturas= listOf(
            FacturaModel("Pagada", 10.25, "25/10/2024"),
            FacturaModel("Pagada", 10.25, "25/10/2024"),
            FacturaModel("Pagada", 10.25, "25/10/2024"),
            FacturaModel("Pagada", 10.25, "25/10/2024")
        )
    }

    @Test
    fun `when the boolean is true so you get everything from RetroMock`() = runBlocking {
        //arrange
        Mockito.`when`(repository.getFacturasFromMock()).thenReturn(facturas)
        val expected = facturas

        //act
        val actual = getFacturasUseCase.invoke(true)

        //assert
        assertEquals(expected, actual)
    }

    @Test
    fun `when the boolean is false so you get everything from API`() = runBlocking {
        //arrange
        Mockito.`when`(repository.getFacturasFromApi()).thenReturn(facturas)
        val expected = facturas

        //act
        val actual = getFacturasUseCase.invoke(false)

        //assert
        assertEquals(expected, actual)
    }

    @Test
    fun `when the boolean is false and facturasFromApi is null so you get this from Room`() = runBlocking {
        //arrange
        Mockito.`when`(repository.getFacturasFromDatabase()).thenReturn(facturas)
        Mockito.`when`(repository.getFacturasFromApi()).thenReturn(null)
        val expected = facturas

        //act
        val actual = getFacturasUseCase.invoke(false)

        //assert
        assertEquals(expected, actual)
    }

    @Test
    fun `when the boolean is false and facturasFromApi is not null, but empty, so you get an emptyList`() =
        runBlocking {
            //arrange
            Mockito.`when`(repository.getFacturasFromApi()).thenReturn(emptyList())
            val expected = emptyList<FacturaModel>()

            //act
            val actual = getFacturasUseCase.invoke(false)

            //assert
            assertEquals(expected, actual)
        }

    @Test
    fun `when the boolean is false, facturasFromApi is null, but you have never charged room so its also null, it has to return an emptyList()`() =
        runBlocking {
            //arrange
            Mockito.`when`(repository.getFacturasFromApi()).thenReturn(null)
            Mockito.`when`(repository.getFacturasFromDatabase()).thenReturn(null)
            val expected = emptyList<FacturaModel>()

            //act
            val actual = getFacturasUseCase.invoke(false)

            //assert
            assertEquals(expected, actual)
        }

    @Test
    fun `when the boolean is true but the Mock has a fail and returns null so it has to return an emptyList()`() =
        runBlocking {
            //arrange
            Mockito.`when`(repository.getFacturasFromMock()).thenReturn(null)
            val expected = emptyList<FacturaModel>()

            //act
            val actual = getFacturasUseCase.invoke(true)

            //assert
            assertEquals(expected, actual)
        }

}