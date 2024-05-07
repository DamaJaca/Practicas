package com.djcdev.practicas.domain.usecase

import com.djcdev.practicas.domain.Repository
import com.djcdev.practicas.domain.model.DetailModel
import javax.inject.Inject

class GetDetailsUseCase @Inject constructor (private val repository: Repository) {
    suspend operator fun invoke(): DetailModel? {
        return repository.getDetails()
    }
}