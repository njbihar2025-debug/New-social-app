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
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.model.PlatformCategory
import com.example.data.model.SavedItemType
import com.example.ui.components.EmptyStateCard
import com.example.ui.components.SectionHeader
import com.example.ui.components.StatusPill
import com.example.ui.viewmodel.SupaulUiState

@Composable
fun SavedScreen(
    uiState: SupaulUiState,
    onRemoveBookmark: (String) -> Unit,
    onOpenCategory: (PlatformCategory) -> Unit,
    modifier: Modifier = Modifier
) {
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
                    text = "सहेजे गए आइटम",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "आपके बुकमार्क की गई खबरें, सरकारी नौकरियां, सेवाएं और बिजनेस",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (uiState.bookmarks.isEmpty()) {
                item {
                    EmptyStateCard(
                        icon = Icons.Filled.BookmarkBorder,
                        title = stringResource(R.string.empty_saved_title),
                        description = stringResource(R.string.empty_saved_desc)
                    )
                }
            } else {
                items(uiState.bookmarks, key = { it.itemKey }) { bookmark ->
                    val type = runCatching { SavedItemType.valueOf(bookmark.itemType) }
                        .getOrDefault(SavedItemType.NEWS)
                    val targetCategory = when (type) {
                        SavedItemType.NEWS -> PlatformCategory.LOCAL_NEWS
                        SavedItemType.JOB -> PlatformCategory.GOVT_JOBS_RESULTS
                        SavedItemType.BUSINESS -> PlatformCategory.BUSINESS_DIRECTORY
                        SavedItemType.SERVICE -> PlatformCategory.BANKING_DIGITAL
                    }

                    Surface(
                        onClick = { onOpenCategory(targetCategory) },
                        color = MaterialTheme.colorScheme.surfaceContainerLowest,
                        shape = MaterialTheme.shapes.large,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.55f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("saved_bookmark_card_${bookmark.itemKey}")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(14.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                StatusPill(
                                    text = type.labelHi,
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = bookmark.title,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = bookmark.subtitle,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            IconButton(
                                onClick = { onRemoveBookmark(bookmark.itemKey) },
                                modifier = Modifier
                                    .minimumInteractiveComponentSize()
                                    .testTag("remove_bookmark_${bookmark.itemKey}")
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.DeleteOutline,
                                    contentDescription = stringResource(R.string.action_remove_bookmark),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }

            if (uiState.adInquiries.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    SectionHeader(
                        title = "आपके विज्ञापन अनुरोध (${uiState.adInquiries.size})",
                        actionLabel = "विज्ञापन पेज",
                        onActionClick = { onOpenCategory(PlatformCategory.ADS_PROMOTION) }
                    )
                }
                items(uiState.adInquiries, key = { "saved_ad_${it.id}" }) { ad ->
                    Surface(
                        onClick = { onOpenCategory(PlatformCategory.ADS_PROMOTION) },
                        color = MaterialTheme.colorScheme.surfaceContainerLowest,
                        shape = MaterialTheme.shapes.medium,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = "${ad.businessName} (${ad.referenceCode})",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${ad.planTitle} • ${ad.status}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}
