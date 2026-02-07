package com.cemede.cemede.domain.data_source

import com.cemede.cemede.domain.util.CoroutineResult

interface CSVDataSource {
    suspend fun getProfessorData(url: String): CoroutineResult<String>
}
