package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.model.PlatformCategory
import com.example.ui.components.EmptyStateCard
import com.example.ui.components.FilterChipStrip
import com.example.ui.components.StatusPill
import com.example.ui.viewmodel.SupaulUiState

private data class SearchResultEntry(
    val id: String,
    val title: String,
    val subtitle: String,
    val categoryLabel: String,
    val category: PlatformCategory
)

@Composable
fun SearchScreen(
    uiState: SupaulUiState,
    query: String,
    onQueryChange: (String) -> Unit,
    onCategoryNavigate: (PlatformCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf("सभी") }
    val filters = listOf(
        "सभी",
        "लोकल न्यूज़",
        "बैंकिंग व डिजिटल",
        "नौकरी व रिजल्ट",
        "बिजनेस",
        "ऑफर"
    )

    val allEntries = remember(uiState) {
        buildList {
            uiState.news.forEach {
                add(
                    SearchResultEntry(
                        id = "s_${it.id}",
                        title = it.title,
                        subtitle = "${it.blockName} • ${it.summary}",
                        categoryLabel = "लोकल न्यूज़",
                        category = PlatformCategory.LOCAL_NEWS
                    )
                )
            }
            uiState.digitalServices.forEach {
                add(
                    SearchResultEntry(
                        id = "s_${it.id}",
                        title = it.title,
                        subtitle = "${it.category} • ${it.subtitle}",
                        categoryLabel = "बैंकिंग व डिजिटल",
                        category = PlatformCategory.BANKING_DIGITAL
                    )
                )
            }
            uiState.bankBranches.forEach {
                add(
                    SearchResultEntry(
                        id = "s_${it.id}",
                        title = "${it.bankName} - ${it.branchName}",
                        subtitle = "IFSC: ${it.ifscCode} • ${it.address}",
                        categoryLabel = "बैंकिंग व डिजिटल",
                        category = PlatformCategory.BANKING_DIGITAL
                    )
                )
            }
            uiState.govtJobs.forEach {
                add(
                    SearchResultEntry(
                        id = "s_${it.id}",
                        title = it.title,
                        subtitle = "${it.department} • ${it.keyDateLabel}",
                        categoryLabel = "नौकरी व रिजल्ट",
                        category = PlatformCategory.GOVT_JOBS_RESULTS
                    )
                )
            }
            uiState.businesses.forEach {
                add(
                    SearchResultEntry(
                        id = "s_${it.id}",
                        title = it.name,
                        subtitle = "${it.category} • ${it.address} (${it.blockName})",
                        categoryLabel = "बिजनेस",
                        category = PlatformCategory.BUSINESS_DIRECTORY
                    )
                )
            }
            uiState.promotions.forEach {
                add(
                    SearchResultEntry(
                        id = "s_${it.id}",
                        title = "${it.businessName}: ${it.title}",
                        subtitle = "${it.discountBadge} • ${it.blockName}",
                        categoryLabel = "ऑफर",
                        category = PlatformCategory.ADS_PROMOTION
                    )
                )
            }
        }
    }

    val filtered = remember(allEntries, query, selectedFilter) {
        val q = query.trim()
        allEntries.filter { entry ->
            val matchesFilter = selectedFilter == "सभी" || entry.categoryLabel == selectedFilter
            val matchesQuery = q.isBlank() ||
                entry.title.contains(q, ignoreCase = true) ||
                entry.subtitle.contains(q, ignoreCase = true) ||
                entry.categoryLabel.contains(q, ignoreCase = true)
            matchesFilter && matchesQuery
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 720.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "सुपौल में खोजें",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "न्यूज़, सरकारी नौकरी, बैंक IFSC, डिजिटल सेवा या लोकल दुकान तुरंत पाएं",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            item {
                OutlinedTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                    trailingIcon = {
                        if (query.isNotEmpty()) {
                            IconButton(
                                onClick = { onQueryChange("") },
                                modifier = Modifier
                                    .minimumInteractiveComponentSize()
                                    .testTag("global_search_clear")
                            ) {
                                Icon(Icons.Filled.Close, contentDescription = "साफ़ करें")
                            }
                        }
                    },
                    placeholder = { Text(stringResource(R.string.search_hint)) },
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("global_search_input")
                )
            }

            item {
                FilterChipStrip(
                    items = filters,
                    selectedItem = selectedFilter,
                    onSelect = { selectedFilter = it },
                    tagPrefix = "global_search_filter"
                )
            }

            if (filtered.isEmpty()) {
                item {
                    EmptyStateCard(
                        icon = Icons.Filled.Search,
                        title = stringResource(R.string.empty_search_title),
                        description = stringResource(R.string.empty_search_desc)
                    )
                }
            } else {
                items(filtered, key = { it.id }) { item ->
                    Surface(
                        onClick = { onCategoryNavigate(item.category) },
                        color = MaterialTheme.colorScheme.surfaceContainerLowest,
                        shape = MaterialTheme.shapes.medium,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("search_result_${item.id}")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(14.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                StatusPill(
                                    text = item.categoryLabel,
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = item.title,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = item.subtitle,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 2
                                )
                            }
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "श्रेणी खोलें",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}
