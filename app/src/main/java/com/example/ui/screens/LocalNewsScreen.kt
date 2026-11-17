package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.model.NewsItem
import com.example.data.model.SavedItemType
import com.example.ui.components.BookmarkIconButton
import com.example.ui.components.CategoryTopBar
import com.example.ui.components.EmptyStateCard
import com.example.ui.components.FilterChipStrip
import com.example.ui.components.StatusPill
import com.example.ui.theme.CrimsonAlert
import com.example.ui.theme.CrimsonContainer
import com.example.ui.util.IntentHelpers
import com.example.ui.viewmodel.SupaulUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocalNewsScreen(
    uiState: SupaulUiState,
    onBackClick: () -> Unit,
    onSelectBlock: (String) -> Unit,
    onToggleBookmark: (NewsItem) -> Unit,
    onCallingClick: () -> Unit
) {
    val context = LocalContext.current
    var selectedArticle by remember { mutableStateOf<NewsItem?>(null) }

    val filteredNews = remember(uiState.news, uiState.selectedBlock) {
        if (uiState.selectedBlock == "सभी प्रखंड") {
            uiState.news
        } else {
            uiState.news.filter { it.blockName == uiState.selectedBlock }
        }
    }

    Scaffold(
        topBar = {
            CategoryTopBar(
                title = stringResource(R.string.cat_local_news),
                subtitle = "सुपौल व सभी प्रखंडों के ताज़ा समाचार",
                onBackClick = onBackClick,
                actions = {
                    IconButton(
                        onClick = onCallingClick,
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .testTag("news_call_action")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Call,
                            contentDescription = "न्यूज़ डेस्क कॉल",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .widthIn(max = 720.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    Card(
                        shape = MaterialTheme.shapes.large,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(138.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_news_kosi),
                                contentDescription = "सुपौल लोकल न्यूज़ बुलेटिन",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(
                                                Color(0x33000000),
                                                Color(0xCC092D38)
                                            )
                                        )
                                    )
                            )
                            Column(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(16.dp)
                            ) {
                                StatusPill(
                                    text = "सुपौल समाचार बुलेटिन",
                                    containerColor = MaterialTheme.colorScheme.secondary,
                                    contentColor = Color.White
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "प्रखंडवार भरोसेमंद खबरें और प्रशासन सूचना",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }

                item {
                    FilterChipStrip(
                        items = uiState.blocks,
                        selectedItem = uiState.selectedBlock,
                        onSelect = onSelectBlock,
                        tagPrefix = "news_block_chip"
                    )
                }

                if (filteredNews.isEmpty()) {
                    item {
                        EmptyStateCard(
                            icon = Icons.Filled.Newspaper,
                            title = "${uiState.selectedBlock} के लिए अभी कोई नई खबर नहीं है",
                            description = "अन्य प्रखंडों की खबरें देखने के लिए 'सभी प्रखंड' चुनें।"
                        )
                    }
                } else {
                    items(filteredNews, key = { it.id }) { article ->
                        val isSaved = uiState.bookmarkedKeys.contains(article.id)
                        NewsArticleCard(
                            article = article,
                            isSaved = isSaved,
                            onClick = { selectedArticle = article },
                            onBookmarkClick = { onToggleBookmark(article) },
                            onShareClick = {
                                IntentHelpers.shareText(
                                    context,
                                    article.title,
                                    "${article.title}\n\n${article.summary}\nप्रखंड: ${article.blockName}"
                                )
                            }
                        )
                    }
                }
            }
        }
    }

    selectedArticle?.let { article ->
        val isSaved = uiState.bookmarkedKeys.contains(article.id)
        ModalBottomSheet(
            onDismissRequest = { selectedArticle = null }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (article.isBreaking) {
                        StatusPill(
                            text = "ब्रेकिंग न्यूज़",
                            containerColor = CrimsonContainer,
                            contentColor = CrimsonAlert
                        )
                    }
                    StatusPill(
                        text = article.blockName,
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    StatusPill(
                        text = article.topicTag,
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "प्रकाशित: ${article.timeAgo}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = article.fullContent,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(18.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { onToggleBookmark(article) },
                        modifier = Modifier
                            .weight(1f)
                            .minimumInteractiveComponentSize()
                            .testTag("news_detail_bookmark_button")
                    ) {
                        Text(if (isSaved) "सहेजा गया" else "खबर सहेजें")
                    }
                    Button(
                        onClick = {
                            IntentHelpers.shareText(
                                context,
                                article.title,
                                "${article.title}\n\n${article.fullContent}"
                            )
                        },
                        modifier = Modifier
                            .weight(1f)
                            .minimumInteractiveComponentSize()
                            .testTag("news_detail_share_button")
                    ) {
                        Text("शेयर करें")
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun NewsArticleCard(
    article: NewsItem,
    isSaved: Boolean,
    onClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        color = MaterialTheme.colorScheme.surfaceContainerLowest,
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.55f)),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("news_card_${article.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (article.isBreaking) {
                        StatusPill(
                            text = "ताज़ा खबर",
                            containerColor = CrimsonContainer,
                            contentColor = CrimsonAlert
                        )
                    }
                    StatusPill(
                        text = article.topicTag,
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    BookmarkIconButton(
                        isSaved = isSaved,
                        onClick = onBookmarkClick,
                        testTag = "news_bookmark_${article.id}"
                    )
                    IconButton(
                        onClick = onShareClick,
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .testTag("news_share_${article.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = "खबर शेयर करें",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = article.summary,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = article.blockName,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Schedule,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = article.timeAgo,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
