package com.cemede.cemede.data.repository

import com.cemede.cemede.data.UrlProvider
import com.cemede.cemede.data.mapper.CsvParser
import com.cemede.cemede.domain.data_base.CemedeDataBase
import com.cemede.cemede.domain.data_source.CSVDataSource
import com.cemede.cemede.domain.model.Partner
import com.cemede.cemede.domain.repository.PartnerRepository
import com.cemede.cemede.domain.util.CoroutineResult
import kotlinx.coroutines.flow.Flow

class PartnerRepositoryImpl(
    private val cemedeDataBase: CemedeDataBase,
    private val csvDataSource: CSVDataSource,
) : PartnerRepository {
    override suspend fun getAllPartnersFlow(): Flow<List<Partner>> = cemedeDataBase.getAllPartnersFlow()

    override suspend fun syncPartnersInfo(): CoroutineResult<Unit> {
        when (val result = csvDataSource.getPartnerData(UrlProvider.partnersTab)) {
            is CoroutineResult.Success -> {
                val rawPartnerEntities = CsvParser.parsePartnersTab(result.data)

                val partnerEntitiesWithIds =
                    rawPartnerEntities.map { entity ->
                        val staffMemberId = cemedeDataBase.getStaffMemberIdByName(entity.staffMemberName)
                        entity.copy(staffMemberId = staffMemberId)
                    }

                cemedeDataBase.upsertAllPartners(partnerEntitiesWithIds)
                return CoroutineResult.Success(Unit)
            }

            is CoroutineResult.Error -> {
                return result
            }
        }
    }
}
