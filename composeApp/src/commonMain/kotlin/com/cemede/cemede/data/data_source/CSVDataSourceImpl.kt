package com.cemede.cemede.data.data_source

import com.cemede.cemede.domain.data_source.CSVDataSource
import com.cemede.cemede.domain.util.CoroutineResult
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

class CSVDataSourceImpl(
    private val httpClient: HttpClient,
) : CSVDataSource {
    override suspend fun getStaffMemberData(url: String): CoroutineResult<String> =
        try {
            val response = httpClient.get(url)
            println("✅ getStaffMemberData from $url, SUCCESS")
            CoroutineResult.Success(response.bodyAsText())
        } catch (e: Exception) {
            println("❌ getStaffMemberData from $url, ERROR")
            CoroutineResult.Error("Error downloading CSV from $url", e)
        }

    override suspend fun getStaffMemberScheduleData(url: String): CoroutineResult<String> =
        try {
            val response = httpClient.get(url)
            println("✅ getStaffMemberScheduleData from $url, SUCCESS")
            CoroutineResult.Success(response.bodyAsText())
        } catch (e: Exception) {
            println("❌ getStaffMemberScheduleData from $url, ERROR")
            CoroutineResult.Error("Error downloading CSV from $url", e)
        }
}
