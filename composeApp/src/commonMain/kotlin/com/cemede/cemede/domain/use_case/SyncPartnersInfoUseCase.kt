package com.cemede.cemede.domain.use_case

import com.cemede.cemede.domain.repository.PartnerRepository
import com.cemede.cemede.domain.util.CoroutineResult
import io.mockative.Mockable

@Mockable
fun interface SyncPartnersInfoUseCase {
    suspend operator fun invoke(): CoroutineResult<Unit>
}

class SyncPartnersInfoUseCaseImpl(
    private val repository: PartnerRepository,
) : SyncPartnersInfoUseCase {
    override suspend operator fun invoke(): CoroutineResult<Unit> = repository.syncPartnersInfo()
}
