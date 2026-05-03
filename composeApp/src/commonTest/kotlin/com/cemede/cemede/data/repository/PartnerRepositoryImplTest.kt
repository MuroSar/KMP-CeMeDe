package com.cemede.cemede.data.repository

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.cemede.cemede.data.UrlProvider
import com.cemede.cemede.domain.data_base.CemedeDataBase
import com.cemede.cemede.domain.data_source.CSVDataSource
import com.cemede.cemede.domain.model.Partner
import com.cemede.cemede.domain.util.CoroutineResult
import io.mockative.any
import io.mockative.coEvery
import io.mockative.mock
import io.mockative.of
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class PartnerRepositoryImplTest {
    private val db = mock(of<CemedeDataBase>())
    private val csvDataSource = mock(of<CSVDataSource>())
    private lateinit var repository: PartnerRepositoryImpl

    @BeforeTest
    fun setUp() {
        repository = PartnerRepositoryImpl(db, csvDataSource)
    }

    @Test
    fun `getAllPartnersFlow should delegate to database`() =
        runTest {
            // Given
            val partners = listOf(Partner(id = 1, name = "Juan Perez", processType = "A"))
            coEvery { db.getAllPartnersFlow() }.returns(flowOf(partners))
            // When
            repository.getAllPartnersFlow().test {
                // Then
                assertThat(awaitItem()).isEqualTo(partners)
                awaitComplete()
            }
        }

    @Test
    fun `syncPartnersInfo success should parse and upsert to db`() =
        runTest {
            // Given
            val csvData =
                """
                Nombre y apellido,Fecha de ingreso,Objetivo,Sindrome,Diagnóstico,Profe,Lunes,Martes,Miercoles,Jueves,Viernes,Tipo de horario
                Juan Perez,01/01/2023,Obj,Sin,Diag,Tomas,12:00,12:00,,,12:00,Fijo
                """.trimIndent()

            coEvery { csvDataSource.getPartnerData(UrlProvider.partnersTab) }.returns(CoroutineResult.Success(csvData))
            coEvery { db.getStaffMemberIdByName("Tomas") }.returns(10)
            coEvery { db.upsertAllPartners(any()) }.returns(Unit)

            // When
            val result = repository.syncPartnersInfo()

            // Then
            assertThat(result).isEqualTo(CoroutineResult.Success(Unit))
        }

    @Test
    fun `syncPartnersInfo error should return result error`() =
        runTest {
            // Given
            val errorResult = CoroutineResult.Error("Network Error")
            coEvery { csvDataSource.getPartnerData(UrlProvider.partnersTab) }.returns(errorResult)

            // When
            val result = repository.syncPartnersInfo()

            // Then
            assertThat(result).isEqualTo(errorResult)
        }
}
