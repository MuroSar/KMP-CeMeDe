package com.cemede.cemede.domain.data_source

import com.cemede.cemede.domain.util.CoroutineResult
import io.mockative.Mockable

@Mockable
interface CSVDataSource {
    suspend fun getStaffMemberData(url: String): CoroutineResult<String>

    suspend fun getStaffMemberScheduleData(url: String): CoroutineResult<String>

    suspend fun getPartnerData(url: String): CoroutineResult<String>
}
