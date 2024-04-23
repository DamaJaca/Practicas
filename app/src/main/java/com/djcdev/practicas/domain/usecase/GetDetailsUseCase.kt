package com.djcdev.practicas.domain.usecase

import com.djcdev.practicas.data.network.MockService
import com.djcdev.practicas.domain.Repository
import com.djcdev.practicas.domain.model.DetailModel
import com.djcdev.practicas.domain.model.FacturaModel
import javax.inject.Inject

class GetDetailsUseCase @Inject constructor (private val repository: Repository) {
    suspend operator fun invoke(): DetailModel? {
        return repository.getDetails()
    }
}