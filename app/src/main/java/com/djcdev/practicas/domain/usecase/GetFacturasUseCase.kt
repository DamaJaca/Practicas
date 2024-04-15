package com.djcdev.practicas.domain.usecase

import com.djcdev.practicas.domain.Repository
import javax.inject.Inject

class GetFacturasUseCase @Inject constructor(private val repository :Repository){

    suspend operator fun invoke () =repository.getFacturas()
}