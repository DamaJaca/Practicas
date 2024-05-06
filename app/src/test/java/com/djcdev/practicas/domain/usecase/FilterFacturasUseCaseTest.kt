package com.djcdev.practicas.domain.usecase

import com.djcdev.practicas.domain.Repository
import com.djcdev.practicas.domain.model.FacturaModel
import kotlinx.coroutines.runBlocking
import net.bytebuddy.matcher.ElementMatchers.any
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.BeforeEach
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.text.SimpleDateFormat

@RunWith(MockitoJUnitRunner::class)
class FilterFacturasUseCaseTest {

    private lateinit var repository:Repository
    private lateinit var filterFacturasUseCase: FilterFacturasUseCase
    private lateinit var facturas:List<FacturaModel>
    private lateinit var simple : SimpleDateFormat


    @Before
    fun onBefore() {
        simple = Mockito.mock(SimpleDateFormat::class.java)
        repository = Mockito.mock(Repository::class.java)
        filterFacturasUseCase = FilterFacturasUseCase(repository)
        facturas = listOf(
            FacturaModel("Pagada", 50.20, "25/10/2024"),
            FacturaModel("Pendiente de pago", 60.10, "25/11/2024"),
            FacturaModel("Pagada", 10.25, "25/11/2024"),
            FacturaModel("Pendiente de pago", 10.25, "25/10/2024")
        )
    }


    @Test
    fun `when we get a model with all null, we get the whole list`() = runBlocking {
        //arrange
        Mockito.`when`(repository.getFacturasFromDatabase()).thenReturn(facturas)
        val expected = facturas

        //act
        val actual = filterFacturasUseCase.invoke(null, null, null, null, null, false)

        //assert
        assertEquals(expected, actual)
    }


    @Test
    fun `when we get a model with all null, we get the whole list from Mock`() = runBlocking {
        //arrange
        Mockito.`when`(repository.getFacturasFromMock()).thenReturn(facturas)
        val expected = facturas

        //act
        val actual = filterFacturasUseCase.invoke(null, null, null, null, null, true)

        //assert
        assertEquals(expected, actual)
    }
    @Test
    fun `when we get a model with an up to pay filter`() = runBlocking {
        //arrange
        Mockito.`when`(repository.getFacturasFromDatabase()).thenReturn(facturas)
        val expected = listOf(
            FacturaModel("Pendiente de pago", 60.10, "25/11/2024"),
            FacturaModel("Pendiente de pago", 10.25, "25/10/2024")
        )

        //act
        val actual = filterFacturasUseCase.invoke(true, null, null, null, null, false)

        //assert
        assertEquals(expected, actual)
    }
    @Test
    fun `when we get a model with an paid filter`() = runBlocking {
        //arrange
        Mockito.`when`(repository.getFacturasFromDatabase()).thenReturn(facturas)
        val expected = listOf(
            FacturaModel("Pagada", 50.20, "25/10/2024"),
            FacturaModel("Pagada", 10.25, "25/11/2024")
        )

        //act
        val actual = filterFacturasUseCase.invoke(null, true, null, null, null, false)

        //assert
        assertEquals(expected, actual)
    }
    @Test
    fun `when we get a model with max import filter`() = runBlocking {
        //arrange
        Mockito.`when`(repository.getFacturasFromDatabase()).thenReturn(facturas)
        val expected = listOf(
            FacturaModel("Pagada", 50.20, "25/10/2024"),
            FacturaModel("Pagada", 10.25, "25/11/2024"),
            FacturaModel("Pendiente de pago", 10.25, "25/10/2024")
        )

        //act
        val actual = filterFacturasUseCase.invoke(null, null, 55.5, null, null, false)

        //assert
        assertEquals(expected, actual)
    }
    @Test
    fun `when we get a model with a date and you get a list`() = runBlocking {
        //No podemos tener solo fecha de inicio o fecha de fin, dado que está contemplado eso en la ui, si cambias fecha inicio o fecha fin
        //por algo que no sea lo preestablecio, la otra se pondrá automáticamente (misma fecha si cambiaste fecha final, fecha actual si cambiaste la inicial)


        //arrange
        Mockito.`when`(repository.getFacturasFromDatabase()).thenReturn(facturas)
        val expected = listOf(
            FacturaModel("Pagada", 50.20, "25/10/2024"),
            FacturaModel("Pendiente de pago", 10.25, "25/10/2024")
        )

        //act
        val actual = filterFacturasUseCase.invoke(null, null, null, "25/10/2024", "26/10/2024", false)

        //assert
        assertEquals(expected, actual)
    }
    @Test
    fun `when we get a model with a date and you get an emptyList()`() = runBlocking {
        //No podemos tener solo fecha de inicio o fecha de fin, dado que está contemplado eso en la ui, si cambias fecha inicio o fecha fin
        //por algo que no sea lo preestablecio, la otra se pondrá automáticamente (misma fecha si cambiaste fecha final, fecha actual si cambiaste la inicial)


        //arrange
        Mockito.`when`(repository.getFacturasFromDatabase()).thenReturn(facturas)
        val expected = emptyList<FacturaModel>()

        //act
        val actual = filterFacturasUseCase.invoke(null, null, null, "25/10/2024", "20/10/2024", false)

        //assert
        assertEquals(expected, actual)
    }
    @Test
    fun `when we get a model with a date but it doesnt have the right format (You will get an emptyList())`() = runBlocking {
        //No podemos tener solo fecha de inicio o fecha de fin, dado que está contemplado eso en la ui, si cambias fecha inicio o fecha fin
        //por algo que no sea lo preestablecio, la otra se pondrá automáticamente (misma fecha si cambiaste fecha final, fecha actual si cambiaste la inicial)


        //arrange
        Mockito.`when`(repository.getFacturasFromDatabase()).thenReturn(facturas)
        val expected = emptyList<FacturaModel>()

        //act
        val actual = filterFacturasUseCase.invoke(null, null, null, null, "20/10/2024", false)

        //assert
        assertEquals(expected, actual)
    }
    @Test
    fun `when we get a model with a date and up to pay so you get a list`() = runBlocking {
        //No podemos tener solo fecha de inicio o fecha de fin, dado que está contemplado eso en la ui, si cambias fecha inicio o fecha fin
        //por algo que no sea lo preestablecio, la otra se pondrá automáticamente (misma fecha si cambiaste fecha final, fecha actual si cambiaste la inicial)


        //arrange
        Mockito.`when`(repository.getFacturasFromDatabase()).thenReturn(facturas)
        val expected = listOf(
            FacturaModel("Pendiente de pago", 10.25, "25/10/2024")
        )

        //act
        val actual = filterFacturasUseCase.invoke(true, null, null, "25/10/2024", "26/10/2024", false)

        //assert
        assertEquals(expected, actual)
    }
    @Test
    fun `when we get a model with a date and up to pay so you get an emptyLlist`() = runBlocking {
        //No podemos tener solo fecha de inicio o fecha de fin, dado que está contemplado eso en la ui, si cambias fecha inicio o fecha fin
        //por algo que no sea lo preestablecio, la otra se pondrá automáticamente (misma fecha si cambiaste fecha final, fecha actual si cambiaste la inicial)


        //arrange
        Mockito.`when`(repository.getFacturasFromDatabase()).thenReturn(facturas)
        val expected = emptyList<FacturaModel>()

        //act
        val actual = filterFacturasUseCase.invoke(true, null, null, "25/10/2024", "20/10/2024", false)

        //assert
        assertEquals(expected, actual)
    }
    @Test
    fun `when we get a model with a date and paid so you get a list`() = runBlocking {
        //No podemos tener solo fecha de inicio o fecha de fin, dado que está contemplado eso en la ui, si cambias fecha inicio o fecha fin
        //por algo que no sea lo preestablecio, la otra se pondrá automáticamente (misma fecha si cambiaste fecha final, fecha actual si cambiaste la inicial)


        //arrange
        Mockito.`when`(repository.getFacturasFromDatabase()).thenReturn(facturas)
        val expected = listOf(
            FacturaModel("Pagada", 50.20, "25/10/2024")
        )

        //act
        val actual = filterFacturasUseCase.invoke(null, true, null, "25/10/2024", "26/10/2024", false)

        //assert
        assertEquals(expected, actual)
    }
    @Test
    fun `when we get a model with a date and paid so you get an emptyList`() = runBlocking {
        //No podemos tener solo fecha de inicio o fecha de fin, dado que está contemplado eso en la ui, si cambias fecha inicio o fecha fin
        //por algo que no sea lo preestablecio, la otra se pondrá automáticamente (misma fecha si cambiaste fecha final, fecha actual si cambiaste la inicial)


        //arrange
        Mockito.`when`(repository.getFacturasFromDatabase()).thenReturn(facturas)
        val expected = emptyList<FacturaModel>()

        //act
        val actual = filterFacturasUseCase.invoke(null, true, null, "25/10/2024", "20/10/2024", false)

        //assert
        assertEquals(expected, actual)
    }
    @Test
    fun `when we get a model with a date and max import so you get a list`() = runBlocking {
        //No podemos tener solo fecha de inicio o fecha de fin, dado que está contemplado eso en la ui, si cambias fecha inicio o fecha fin
        //por algo que no sea lo preestablecio, la otra se pondrá automáticamente (misma fecha si cambiaste fecha final, fecha actual si cambiaste la inicial)


        //arrange
        Mockito.`when`(repository.getFacturasFromDatabase()).thenReturn(facturas)
        val expected = listOf(
            FacturaModel("Pagada", 50.20, "25/10/2024"),
            FacturaModel("Pendiente de pago", 10.25, "25/10/2024")
        )

        //act
        val actual = filterFacturasUseCase.invoke(null, null, 55.0, "25/10/2024", "26/10/2024", false)

        //assert
        assertEquals(expected, actual)
    }
    @Test
    fun `when we get a model with a date and max import so you get an emtptyList`() = runBlocking {
        //No podemos tener solo fecha de inicio o fecha de fin, dado que está contemplado eso en la ui, si cambias fecha inicio o fecha fin
        //por algo que no sea lo preestablecio, la otra se pondrá automáticamente (misma fecha si cambiaste fecha final, fecha actual si cambiaste la inicial)


        //arrange
        Mockito.`when`(repository.getFacturasFromDatabase()).thenReturn(facturas)
        val expected = emptyList<FacturaModel>()

        //act
        val actual = filterFacturasUseCase.invoke(null, null, 55.0, "25/10/2024", "20/10/2024", false)

        //assert
        assertEquals(expected, actual)
    }
    @Test
    fun `when we get a model with a up to pay and max import so you get a list`() = runBlocking {

        //arrange
        Mockito.`when`(repository.getFacturasFromDatabase()).thenReturn(facturas)
        val expected = listOf(
            FacturaModel("Pendiente de pago", 10.25, "25/10/2024")
        )

        //act
        val actual = filterFacturasUseCase.invoke(true, null, 55.0, null, null, false)

        //assert
        assertEquals(expected, actual)
    }
    @Test
    fun `when we get a model with a up to pay and max import so you get an emptyList`() = runBlocking {

        //arrange
        Mockito.`when`(repository.getFacturasFromDatabase()).thenReturn(facturas)
        val expected = emptyList<FacturaModel>()

        //act
        val actual = filterFacturasUseCase.invoke(true, null, 5.5, null, null, false)

        //assert
        assertEquals(expected, actual)
    }
    @Test
    fun `when we get a model with a paid and max import so you get a list`() = runBlocking {

        //arrange
        Mockito.`when`(repository.getFacturasFromDatabase()).thenReturn(facturas)
        val expected = listOf(
            FacturaModel("Pagada", 50.20, "25/10/2024"),
            FacturaModel("Pagada", 10.25, "25/11/2024"),
        )

        //act
        val actual = filterFacturasUseCase.invoke(null, true, 55.0, null, null, false)

        //assert
        assertEquals(expected, actual)
    }
    @Test
    fun `when we get a model with a paid and max import so you get an emptyList`() = runBlocking {

        //arrange
        Mockito.`when`(repository.getFacturasFromDatabase()).thenReturn(facturas)
        val expected = emptyList<FacturaModel>()

        //act
        val actual = filterFacturasUseCase.invoke(null, true, 5.0, null, null, false)

        //assert
        assertEquals(expected, actual)
    }
    @Test
    fun `when we get a model with a paid, up to pay and max import so you get a list`() = runBlocking {

        //arrange
        Mockito.`when`(repository.getFacturasFromDatabase()).thenReturn(facturas)
        val expected = listOf(
            FacturaModel("Pendiente de pago", 10.25, "25/10/2024"),
            FacturaModel("Pagada", 50.20, "25/10/2024"),
            FacturaModel("Pagada", 10.25, "25/11/2024")
        )

        //act
        val actual = filterFacturasUseCase.invoke(true, true, 55.0, null, null, false)

        //assert
        assertEquals(expected, actual)
    }
    @Test
    fun `when we get a model with a paid, up to pay and max import so you get an emptyList`() = runBlocking {

        //arrange
        Mockito.`when`(repository.getFacturasFromDatabase()).thenReturn(facturas)
        val expected = emptyList<FacturaModel>()

        //act
        val actual = filterFacturasUseCase.invoke(true, true, 5.0, null, null, false)

        //assert
        assertEquals(expected, actual)
    }
    @Test
    fun `when we get a model full of filters so you get a list`() = runBlocking {

        //arrange
        Mockito.`when`(repository.getFacturasFromDatabase()).thenReturn(facturas)
        val expected = listOf(
            FacturaModel("Pendiente de pago", 10.25, "25/10/2024")
        )

        //act
        val actual = filterFacturasUseCase.invoke(true, true, 12.0, "25/10/2024", "26/10/2024", false)

        //assert
        assertEquals(expected, actual)
    }
    @Test
    fun `when we get a model full of filters so you get an emptyList`() = runBlocking {

        //arrange
        Mockito.`when`(repository.getFacturasFromDatabase()).thenReturn(facturas)
        val expected = emptyList<FacturaModel>()

        //act
        val actual = filterFacturasUseCase.invoke(true, true, 12.0, "25/10/2024", "20/10/2024", false)

        //assert
        assertEquals(expected, actual)
    }


}