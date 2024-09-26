package com.example.pouet

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySearchBar(windowClass: WindowSizeClass, viewModel: MainViewModel) {
    if (viewModel.isSearching) {
        SearchBar(
            query = viewModel.searchText,
            onQueryChange = { viewModel.searchText = it },
            onSearch = { viewModel.onSearch() },
            active = false,
            onActiveChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .height(60.dp),
            leadingIcon = {
                IconButton(onClick = { viewModel.onSearch() }) {
                    Icon(Icons.Filled.Search, contentDescription = "Search", tint = colorResource(id = R.color.teal_700))
                }
            },
            trailingIcon = {
                IconButton(onClick = { viewModel.closeSearchBar() }) {
                    Icon(Icons.Filled.Close, contentDescription = "Close", tint = colorResource(id = R.color.teal_700))
                }
            },
            placeholder = { Text("Search") },
            colors = SearchBarColors(containerColor = Color.LightGray, dividerColor = Color.Transparent)
        ) { }
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { viewModel.openSearchBar() },
                modifier = Modifier.height(60.dp),
                contentPadding = PaddingValues(start = 15.dp, end = 5.dp, top = 5.dp, bottom = 5.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = colorResource(id = R.color.teal_700),
                    containerColor = Color.LightGray,
                )
            ) {
                Text(text = stringResource(id = R.string.search_text), color = Color.DarkGray, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(end = 10.dp))
                Icon(Icons.Filled.Search, contentDescription = "Search", modifier = Modifier.size(40.dp))
            }
        }
    }
}

