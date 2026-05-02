package com.cemede.cemede.domain.use_case

import com.cemede.cemede.domain.model.Partner
import com.cemede.cemede.domain.repository.PartnerRepository
import io.mockative.Mockable
import kotlinx.coroutines.flow.Flow

@Mockable
fun interface GetAllPartnersUseCase {
    suspend operator fun invoke(): Flow<List<Partner>>
}

class GetAllPartnersUseCaseImpl(
    private val repository: PartnerRepository,
) : GetAllPartnersUseCase {
    override suspend operator fun invoke(): Flow<List<Partner>> = repository.getAllPartnersFlow()
}
