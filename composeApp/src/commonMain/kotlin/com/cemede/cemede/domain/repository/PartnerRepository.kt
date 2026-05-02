package com.cemede.cemede.domain.repository

import com.cemede.cemede.domain.model.Partner
import com.cemede.cemede.domain.model.StaffMember
import com.cemede.cemede.domain.util.CoroutineResult
import io.mockative.Mockable
import kotlinx.coroutines.flow.Flow

@Mockable
interface PartnerRepository {
    suspend fun getAllPartnersFlow(): Flow<List<Partner>>

    suspend fun syncPartnersInfo(): CoroutineResult<Unit>
}
