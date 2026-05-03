package com.cemede.cemede.data.data_base

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.cemede.cemede.data.data_base.dao.CemedeDao
import com.cemede.cemede.data.data_base.model.PartnerEntity
import com.cemede.cemede.data.data_base.model.StaffMemberAndPartners
import com.cemede.cemede.data.data_base.model.StaffMemberEntity
import io.mockative.coEvery
import io.mockative.every
import io.mockative.mock
import io.mockative.of
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class CemedeDataBaseImplTest {
    private val dao = mock(of<CemedeDao>())
    private lateinit var dataBase: CemedeDataBaseImpl

    @BeforeTest
    fun setUp() {
        dataBase = CemedeDataBaseImpl(dao)
    }

    @Test
    fun `getAllStaffMembersFlow should map dao results to domain`() =
        runTest {
            // Given
            val staffEntity = StaffMemberEntity(id = 1, name = "Tomas")
            val partnerEntities = listOf(PartnerEntity(id = 1, name = "Partner", processType = "A", staffMemberId = 1))
            val daoResult = listOf(StaffMemberAndPartners(staffEntity, partnerEntities))

            every { dao.getAllStaffMembersAndPartners() }.returns(flowOf(daoResult))

            // When
            dataBase.getAllStaffMembersFlow().test {
                val result = awaitItem()
                // Then
                assertThat(result.size).isEqualTo(1)
                assertThat(result.first().name).isEqualTo("Tomas")
                assertThat(
                    result
                        .first()
                        .partners
                        .first()
                        .name,
                ).isEqualTo("Partner")
                awaitComplete()
            }
        }

    @Test
    fun `getStaffMemberIdByName should delegate to dao`() =
        runTest {
            // Given
            val name = "Tomas"
            coEvery { dao.getStaffMemberIdByName(name) }.returns(42)

            // When
            val result = dataBase.getStaffMemberIdByName(name)

            // Then
            assertThat(result).isEqualTo(42)
        }

    @Test
    fun `upsertStaffMember should delegate to dao`() =
        runTest {
            // Given
            val entity = StaffMemberEntity(name = "New")
            coEvery { dao.upsertStaffMember(entity) }.returns(1L)

            // When
            val result = dataBase.upsertStaffMember(entity)

            // Then
            assertThat(result).isEqualTo(1L)
        }
}
