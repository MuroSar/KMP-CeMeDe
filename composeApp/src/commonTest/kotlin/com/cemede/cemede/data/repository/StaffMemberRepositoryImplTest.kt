package com.cemede.cemede.data.repository

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.cemede.cemede.domain.data_base.CemedeDataBase
import com.cemede.cemede.domain.data_source.CSVDataSource
import com.cemede.cemede.domain.model.StaffMember
import com.cemede.cemede.domain.util.CoroutineResult
import io.mockative.any
import io.mockative.coEvery
import io.mockative.mock
import io.mockative.of
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class StaffMemberRepositoryImplTest {
    private val db = mock(of<CemedeDataBase>())
    private val csvDataSource = mock(of<CSVDataSource>())
    private lateinit var repository: StaffMemberRepositoryImpl

    @BeforeTest
    fun setUp() {
        repository = StaffMemberRepositoryImpl(db, csvDataSource)
    }

    @Test
    fun `getAllStaffMembersFlow should delegate to database`() =
        runTest {
            val staffMembers = listOf(StaffMember(id = 1, name = "Dr. House"))
            coEvery { db.getAllStaffMembersFlow() }.returns(flowOf(staffMembers))

            repository.getAllStaffMembersFlow().test {
                assertThat(awaitItem()).isEqualTo(staffMembers)
                awaitComplete()
            }
        }

    @Test
    fun `syncStaffMembersWorkingSchedule success should update db`() =
        runTest {
            val csvData = "Profesional,Lunes,Martes,Miercoles,Jueves,Viernes\nTomas,12:00,12:00,12:00,12:00,12:00"
            coEvery { csvDataSource.getStaffMemberScheduleData(any()) }.returns(CoroutineResult.Success(csvData))
            coEvery { db.getAllStaffMembersFlow() }.returns(flowOf(emptyList()))
            coEvery { db.upsertStaffMember(any()) }.returns(1L)

            val result = repository.syncStaffMembersWorkingSchedule()
            assertThat(result).isEqualTo(CoroutineResult.Success(Unit))
        }

    @Test
    fun `syncStaffMemberInfo error on first call should return error`() =
        runTest {
            val staffMember = StaffMember(id = 1, name = "Tomas")
            val errorResult = CoroutineResult.Error("Network Error")

            coEvery { csvDataSource.getStaffMemberData(any()) }.returns(errorResult)

            val result = repository.syncStaffMemberInfo(staffMember)
            assertThat(result).isEqualTo(errorResult)
        }

    @Test
    fun `syncAllStaffMembersSchedule should handle multiple syncs`() =
        runTest {
            val staffMembers = listOf(StaffMember(id = 1, name = "Tomas"))
            coEvery { db.getAllStaffMembersFlow() }.returns(flowOf(staffMembers))

            // Mocking database lookups for partner names found in CSV
            coEvery { db.getPartnerByName(any()) }.returns(null)

            val csvData = "Hora,Lunes,Martes,Miercoles,Jueves,Viernes\n08:00,Paciente,A,B,C,D"
            coEvery { csvDataSource.getStaffMemberScheduleData(any()) }.returns(CoroutineResult.Success(csvData))

            coEvery { db.upsertStaffMember(any()) }.returns(1L)

            val result = repository.syncAllStaffMembersSchedule()
            assertThat(result).isEqualTo(CoroutineResult.Success(Unit))
        }
}
