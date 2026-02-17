package com.cemede.cemede.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.cemede.cemede.presentation.theme.CemedeTheme
import com.cemede.cemede.presentation.theme.font_size_16
import com.cemede.cemede.presentation.theme.padding_16
import com.cemede.cemede.presentation.theme.padding_8
import com.cemede.cemede.presentation.theme.space_16

object CemedeSearchBar {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SearchBar(
        modifier: Modifier = Modifier,
        placeholder: String,
        searchQuery: String,
        onSearchQueryChange: (String) -> Unit,
        enabled: Boolean = true,
    ) {
        TextField(
            modifier =
                modifier
                    .fillMaxWidth()
                    .padding(horizontal = padding_16, vertical = padding_8),
            enabled = enabled,
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Ícono de buscar",
                )
            },
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = font_size_16,
                )
            },
        )
    }
}

@Preview
@Composable
private fun SearchBarPreview() {
    CemedeTheme {
        Column(modifier = Modifier.padding(padding_16)) {
            CemedeSearchBar.SearchBar(
                placeholder = "Buscar profes... (Enabled)",
                searchQuery = "",
                onSearchQueryChange = {},
                enabled = true,
            )
            Spacer(modifier = Modifier.height(space_16))
            CemedeSearchBar.SearchBar(
                placeholder = "Buscar profes... (Disabled)",
                searchQuery = "",
                onSearchQueryChange = {},
                enabled = false,
            )
        }
    }
}
