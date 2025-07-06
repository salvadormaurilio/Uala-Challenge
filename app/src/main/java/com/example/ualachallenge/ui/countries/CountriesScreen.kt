package com.example.ualachallenge.ui.countries

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ualachallenge.R
import com.example.ualachallenge.core.extensions.empty
import com.example.ualachallenge.data.datasource.exception.DataException
import com.example.ualachallenge.domain.model.Country
import com.example.ualachallenge.ui.theme.CountriesChallengeTheme
import com.example.ualachallenge.ui.views.CircularProgressIndicatorFixMax
import com.example.ualachallenge.ui.views.CountriesErrorScreen

@Composable
fun CountriesScreen(viewModel: CountriesViewModel = hiltViewModel()) {

    val uiState = viewModel.countriesUiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initGetCountries()
    }

    CountriesContent(
        isActiveSearch = viewModel.isActiveSearch,
        query = viewModel.query,
        filterFavorites = viewModel.filterFavorites,
        listState = viewModel.listState,
        isLoading = uiState.value.isLoading,
        countries = uiState.value.countries,
        error = uiState.value.error,
        onActiveSearch = viewModel::activeSearch,
        onSearch = viewModel::searchCountries,
        onFilterFavorites = viewModel::filterFavorites,
        onFavorite = viewModel::updateFavorite,
        onRetry = viewModel::retryGetCountries,
    )
}

@Composable
private fun CountriesContent(
    isActiveSearch: Boolean = false,
    query: String = String.empty(),
    filterFavorites: Boolean = false,
    listState: LazyListState = LazyListState(),
    isLoading: Boolean = false,
    countries: List<Country>? = null,
    error: Throwable? = null,
    onActiveSearch: (Boolean) -> Unit = {},
    onSearch: (String) -> Unit = {},
    onFilterFavorites: (Boolean) -> Unit = { _ -> },
    onFavorite: (Int, Boolean) -> Unit = { _, _ -> },
    onShorCoordinates: (Country) -> Unit = {},
    onShowDetails: (Country) -> Unit = {},
    onRetry: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            CountriesTopAppBar(
                isActiveSearch = isActiveSearch,
                query = query,
                onActiveSearch = onActiveSearch,
                onSearch = onSearch
            )
        },
        content = { paddingValues ->
            Countries(
                modifier = Modifier.padding(paddingValues),
                filterFavorites = filterFavorites,
                listState = listState,
                countries = countries,
                onFilterFavorites = onFilterFavorites,
                onFavorite = onFavorite,
                onShorCoordinates = onShorCoordinates,
                onShowDetails = onShowDetails
            )

            CountriesErrorScreen(
                modifier = Modifier.padding(paddingValues),
                error = error,
                onRetry = onRetry
            )

            CircularProgressIndicatorFixMax(
                modifier = Modifier.padding(paddingValues),
                isVisible = isLoading
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CountriesTopAppBar(
    isActiveSearch: Boolean,
    query: String,
    onActiveSearch: (Boolean) -> Unit = {},
    onSearch: (String) -> Unit = {},
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        title = {
            SearchTextField(isActiveSearch, query, onActiveSearch, onSearch)
        },
        actions = {
            if (!isActiveSearch) {
                IconButton(onClick = { onActiveSearch(true) }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        tint = MaterialTheme.colorScheme.surface,
                        contentDescription = String.empty()
                    )
                }
            }
        },
    )
}

@Composable
private fun SearchTextField(
    isActiveSearch: Boolean,
    query: String,
    onActiveSearch: (Boolean) -> Unit,
    onSearch: (String) -> Unit
) {
    if (!isActiveSearch) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    } else {
        TextField(
            modifier = Modifier
                .padding(end = 16.dp)
                .fillMaxWidth()
                .focusRequester(FocusRequester()),
            textStyle = MaterialTheme.typography.titleSmall,
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
            ),
            value = query,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search),
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            leadingIcon = {
                IconButton(onClick = {
                    onActiveSearch(false)
                    onSearch(String.empty())
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = String.empty(),
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = { onSearch(String.empty()) }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = String.empty()
                    )
                }
            },
            onValueChange = { onSearch(it) },
        )
    }
}

@Composable
private fun Countries(
    modifier: Modifier = Modifier,
    filterFavorites: Boolean,
    listState: LazyListState,
    countries: List<Country>?,
    onFilterFavorites: (Boolean) -> Unit = { _ -> },
    onFavorite: (Int, Boolean) -> Unit = { _, _ -> },
    onShorCoordinates: (Country) -> Unit = {},
    onShowDetails: (Country) -> Unit = {}
) {
    if (countries == null) return
    Column(
        modifier = modifier,
    ) {
        FavoriteFilterSwitch(
            filterFavorites = filterFavorites,
            onFilterFavorites = onFilterFavorites
        )

        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp),
            state = listState
        ) {
            items(items = countries) {
                CountryItem(
                    country = it,
                    onShorCoordinates = onShorCoordinates,
                    onShowDetails = onShowDetails,
                    onFavorite = onFavorite
                )
            }
        }
    }
}

@Composable
private fun FavoriteFilterSwitch(
    filterFavorites: Boolean = false,
    onFilterFavorites: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = 12.dp, alignment = Alignment.End)
    ) {
        Text(
            text = stringResource(R.string.only_favourites),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary,
        )

        Switch(
            checked = filterFavorites,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.background,
            ),
            onCheckedChange = { onFilterFavorites(it) },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CountriesContentUiStateLoadingPreview() {
    CountriesChallengeTheme {
        CountriesContent(
            isLoading = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CountriesContentUiStateSuccessPreview() {
    var isActiveSearch by rememberSaveable { mutableStateOf(false) }
    var query by rememberSaveable { mutableStateOf(String.empty()) }
    var filterFavorites by rememberSaveable { mutableStateOf(false) }
    CountriesChallengeTheme {
        CountriesContent(
            isActiveSearch = isActiveSearch,
            query = query,
            filterFavorites = filterFavorites,
            countries = getCountriesTestData(),
            onSearch = { query = it },
            onActiveSearch = { isActiveSearch = it },
            onFilterFavorites = { filterFavorites = it }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CountriesContentUiStateErrorPreview() {
    CountriesChallengeTheme {
        CountriesContent(
            error = DataException.CountriesException()
        )
    }
}
