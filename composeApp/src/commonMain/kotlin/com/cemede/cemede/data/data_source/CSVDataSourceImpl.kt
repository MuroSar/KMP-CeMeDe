package com.cemede.cemede.data.data_source

import com.cemede.cemede.domain.data_source.CSVDataSource
import com.cemede.cemede.domain.util.CoroutineResult
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

class CSVDataSourceImpl(
    private val httpClient: HttpClient,
) : CSVDataSource {
    override suspend fun getProfessorData(url: String): CoroutineResult<String> =
        try {
            val response = httpClient.get(url)
            println("✅ getProfessorData from $url, SUCCESS")
            CoroutineResult.Success(response.bodyAsText())
        } catch (e: Exception) {
            println("❌ getProfessorData from $url, ERROR")
            CoroutineResult.Error("Error downloading CSV from $url", e)
        }
}
