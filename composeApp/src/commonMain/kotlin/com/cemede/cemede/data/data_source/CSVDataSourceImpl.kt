package com.cemede.cemede.data.data_source

import com.cemede.cemede.domain.data_source.CSVDataSource
import com.cemede.cemede.domain.model.CoroutineResult
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

class CSVDataSourceImpl(
    private val httpClient: HttpClient,
) : CSVDataSource {
    override suspend fun getProfessorData(url: String): CoroutineResult<String> =
        try {
            val response = httpClient.get(url)
            CoroutineResult.Success(response.bodyAsText())
        } catch (e: Exception) {
            CoroutineResult.Error("Error downloading CSV from $url", e)
        }
}
