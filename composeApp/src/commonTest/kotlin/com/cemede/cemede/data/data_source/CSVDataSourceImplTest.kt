package com.cemede.cemede.data.data_source

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.cemede.cemede.domain.util.CoroutineResult
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class CSVDataSourceImplTest {
    private fun createMockClient(
        content: String,
        status: HttpStatusCode = HttpStatusCode.OK,
    ): HttpClient {
        val mockEngine =
            MockEngine { request ->
                respond(
                    content = content,
                    status = status,
                    headers = headersOf("Content-Type", "text/csv"),
                )
            }
        return HttpClient(mockEngine)
    }

    @Test
    fun `getPartnerData should return success when network call succeeds`() =
        runTest {
            val expectedData = "csv,data"
            val client = createMockClient(expectedData)
            val dataSource = CSVDataSourceImpl(client)

            val result = dataSource.getPartnerData("https://test.com")

            assertThat(result).isEqualTo(CoroutineResult.Success(expectedData))
        }

    @Test
    fun `getPartnerData should return error when network call fails`() =
        runTest {
            val client = createMockClient("", status = HttpStatusCode.InternalServerError)
            val dataSource = CSVDataSourceImpl(client)

            val result = dataSource.getPartnerData("https://test.com")

            assertThat(result).isInstanceOf(CoroutineResult.Error::class)
        }
}
