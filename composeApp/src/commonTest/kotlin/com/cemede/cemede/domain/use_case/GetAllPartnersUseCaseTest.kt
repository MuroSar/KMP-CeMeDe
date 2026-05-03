package com.cemede.cemede.domain.use_case

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.cemede.cemede.domain.model.Partner
import com.cemede.cemede.domain.repository.PartnerRepository
import io.mockative.coEvery
import io.mockative.mock
import io.mockative.of
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class GetAllPartnersUseCaseTest {
    private val repository = mock(of<PartnerRepository>())
    private lateinit var useCase: GetAllPartnersUseCase

    @BeforeTest
    fun setUp() {
        useCase = GetAllPartnersUseCaseImpl(repository)
    }

    @Test
    fun `invoke should return flow of partners from repository`() =
        runTest {
            // Given
            val expectedPartners =
                listOf(
                    Partner(id = 1, name = "Socio 1", processType = "A"),
                    Partner(id = 2, name = "Socio 2", processType = "B"),
                )
            coEvery { repository.getAllPartnersFlow() }.returns(flowOf(expectedPartners))

            // When
            val result = useCase().first()

            // Then
            assertThat(result).isEqualTo(expectedPartners)
        }
}
