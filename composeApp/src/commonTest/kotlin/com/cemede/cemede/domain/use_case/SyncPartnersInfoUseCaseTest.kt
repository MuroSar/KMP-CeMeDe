package com.cemede.cemede.domain.use_case

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.cemede.cemede.domain.repository.PartnerRepository
import com.cemede.cemede.domain.util.CoroutineResult
import io.mockative.coEvery
import io.mockative.mock
import io.mockative.of
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class SyncPartnersInfoUseCaseTest {
    private val repository = mock(of<PartnerRepository>())
    private lateinit var useCase: SyncPartnersInfoUseCase

    @BeforeTest
    fun setUp() {
        useCase = SyncPartnersInfoUseCaseImpl(repository)
    }

    @Test
    fun `invoke should return success from repository`() =
        runTest {
            // Given
            coEvery { repository.syncPartnersInfo() }.returns(CoroutineResult.Success(Unit))

            // When
            val result = useCase()

            // Then
            assertThat(result).isEqualTo(CoroutineResult.Success(Unit))
        }

    @Test
    fun `invoke should return error from repository`() =
        runTest {
            // Given
            val errorMessage = "Error de red"
            coEvery { repository.syncPartnersInfo() }.returns(CoroutineResult.Error(errorMessage))

            // When
            val result = useCase()

            // Then
            assertThat(result).isEqualTo(CoroutineResult.Error(errorMessage))
        }
}
