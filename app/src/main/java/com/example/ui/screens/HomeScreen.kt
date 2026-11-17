package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.model.PlatformCategory
import com.example.ui.components.SectionHeader
import com.example.ui.components.StatusPill
import com.example.ui.theme.CrimsonAlert
import com.example.ui.theme.CrimsonContainer
import com.example.ui.theme.KosiTeal
import com.example.ui.theme.KosiTealContainer
import com.example.ui.theme.MarigoldContainer
import com.example.ui.theme.MarigoldSaffron
import com.example.ui.theme.MithilaEmerald
import com.example.ui.theme.MithilaEmeraldContainer
import com.example.ui.theme.RoyalIndigo
import com.example.ui.theme.RoyalIndigoContainer
import com.example.ui.util.IntentHelpers
import com.example.ui.viewmodel.SupaulUiState

@Composable
fun HomeScreen(
    uiState: SupaulUiState,
    onCategoryClick: (PlatformCategory) -> Unit,
    onSearchClick: () -> Unit,
    onSavedClick: () -> Unit,
    onCallingClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 720.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Brand Header
            item {
                HomeBrandHeader(
                    savedCount = uiState.bookmarks.size,
                    onSavedClick = onSavedClick,
                    onCallingClick = onCallingClick
                )
            }

            // 2. Hero Welcome Card with Supaul Koshi Banner & Search Trigger
            item {
                HomeHeroBanner(onSearchClick = onSearchClick)
            }

            // 3. Main 5 Categories — Immediately visible on opening the app
            item {
                SectionHeader(
                    title = stringResource(R.string.all_categories_header)
                )
            }

            item {
                CategoryGridShowcase(
                    uiState = uiState,
                    onCategoryClick = onCategoryClick
                )
            }

            // 4. Today's Highlights Carousel
            item {
                SectionHeader(
                    title = stringResource(R.string.today_highlights),
                    actionLabel = "सभी न्यूज़",
                    onActionClick = { onCategoryClick(PlatformCategory.LOCAL_NEWS) }
                )
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val breakingNews = uiState.news.firstOrNull()
                    if (breakingNews != null) {
                        item {
                            HighlightQuickCard(
                                tag = "ताज़ा खबर • ${breakingNews.blockName}",
                                title = breakingNews.title,
                                subtitle = breakingNews.timeAgo,
                                accentColor = KosiTeal,
                                onClick = { onCategoryClick(PlatformCategory.LOCAL_NEWS) },
                                testTag = "highlight_news_card"
                            )
                        }
                    }
                    val urgentJob = uiState.govtJobs.firstOrNull { it.isUrgent }
                    if (urgentJob != null) {
                        item {
                            HighlightQuickCard(
                                tag = "नौकरी अलर्ट • ${urgentJob.postsOrStatus}",
                                title = urgentJob.title,
                                subtitle = urgentJob.keyDateLabel,
                                accentColor = RoyalIndigo,
                                onClick = { onCategoryClick(PlatformCategory.GOVT_JOBS_RESULTS) },
                                testTag = "highlight_job_card"
                            )
                        }
                    }
                    val topPromo = uiState.promotions.firstOrNull()
                    if (topPromo != null) {
                        item {
                            HighlightQuickCard(
                                tag = "ऑफर • ${topPromo.discountBadge}",
                                title = "${topPromo.businessName}: ${topPromo.title}",
                                subtitle = "${topPromo.blockName} • ${topPromo.validTill}",
                                accentColor = MarigoldSaffron,
                                onClick = { onCategoryClick(PlatformCategory.ADS_PROMOTION) },
                                testTag = "highlight_promo_card"
                            )
                        }
                    }
                }
            }

            // 5. Emergency & District Helplines
            item {
                SectionHeader(title = stringResource(R.string.emergency_helpline))
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(uiState.helplines, key = { it.id }) { helpline ->
                        Surface(
                            onClick = { IntentHelpers.dialPhoneNumber(context, helpline.number) },
                            color = MaterialTheme.colorScheme.surfaceContainerLowest,
                            shape = MaterialTheme.shapes.medium,
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f)),
                            modifier = Modifier
                                .width(172.dp)
                                .minimumInteractiveComponentSize()
                                .testTag("helpline_card_${helpline.id}")
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(CircleShape)
                                        .background(CrimsonContainer)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Call,
                                        contentDescription = null,
                                        tint = CrimsonAlert,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = helpline.number,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = helpline.title,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun HomeBrandHeader(
    savedCount: Int,
    onSavedClick: () -> Unit,
    onCallingClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.apna_supaul_icon),
                contentDescription = "Apna Supaul logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(14.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = stringResource(R.string.brand_title),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = stringResource(R.string.brand_subtitle),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                onClick = onCallingClick,
                color = MithilaEmeraldContainer,
                contentColor = MithilaEmerald,
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .minimumInteractiveComponentSize()
                    .testTag("home_calling_button")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Call,
                        contentDescription = "कॉल करें",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "कॉल",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }

            Surface(
                onClick = onSavedClick,
                color = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .minimumInteractiveComponentSize()
                    .testTag("home_saved_pill_button")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Bookmark,
                        contentDescription = "सहेजे गए आइटम",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$savedCount",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeHeroBanner(onSearchClick: () -> Unit) {
    Card(
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(156.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_supaul_hero),
                contentDescription = "सुपौल कोसी क्षेत्र बैनर",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xEC092D38),
                                Color(0xBA0F4C5C),
                                Color(0x450F4C5C)
                            )
                        )
                    )
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    StatusPill(
                        text = "एक ऐप, अनेक सुविधाएं।",
                        containerColor = Color(0xFFF39237),
                        contentColor = Color(0xFF2B1100)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = stringResource(R.string.district_badge),
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.White
                        )
                    }
                }

                Column {
                    Text(
                        text = "सुपौल का हर अपडेट और सेवा,\nअब आपकी मुट्ठी में",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(
                        onClick = onSearchClick,
                        color = Color.White.copy(alpha = 0.96f),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .minimumInteractiveComponentSize()
                            .testTag("home_search_bar_button")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 11.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "खोजें",
                                tint = KosiTeal,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = stringResource(R.string.search_hint),
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF4F5D63),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryGridShowcase(
    uiState: SupaulUiState,
    onCategoryClick: (PlatformCategory) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // Featured #1: लोकल न्यूज़ (Full-width Hero Category Card)
        CategoryCardWide(
            category = PlatformCategory.LOCAL_NEWS,
            icon = Icons.Filled.Newspaper,
            accentColor = KosiTeal,
            containerColor = KosiTealContainer,
            countBadge = "${uiState.news.size} खबरें",
            onClick = { onCategoryClick(PlatformCategory.LOCAL_NEWS) },
            testTag = "category_card_local_news"
        )

        // Row 1: बैंकिंग एवं डिजिटल सर्विस + सरकारी नौकरी एवं रिजल्ट
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CategoryCardCompact(
                category = PlatformCategory.BANKING_DIGITAL,
                icon = Icons.Filled.AccountBalance,
                accentColor = MithilaEmerald,
                containerColor = MithilaEmeraldContainer,
                countBadge = "${uiState.digitalServices.size} सेवाएं • ${uiState.bankBranches.size} बैंक",
                onClick = { onCategoryClick(PlatformCategory.BANKING_DIGITAL) },
                testTag = "category_card_banking_digital",
                modifier = Modifier.weight(1f)
            )
            CategoryCardCompact(
                category = PlatformCategory.GOVT_JOBS_RESULTS,
                icon = Icons.Filled.Work,
                accentColor = RoyalIndigo,
                containerColor = RoyalIndigoContainer,
                countBadge = "${uiState.govtJobs.size} अपडेट",
                onClick = { onCategoryClick(PlatformCategory.GOVT_JOBS_RESULTS) },
                testTag = "category_card_govt_jobs",
                modifier = Modifier.weight(1f)
            )
        }

        // Row 2: लोकल बिजनेस डायरेक्टरी + विज्ञापन एवं प्रमोशन
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CategoryCardCompact(
                category = PlatformCategory.BUSINESS_DIRECTORY,
                icon = Icons.Filled.Storefront,
                accentColor = MarigoldSaffron,
                containerColor = MarigoldContainer,
                countBadge = "${uiState.businesses.size} प्रतिष्ठान",
                onClick = { onCategoryClick(PlatformCategory.BUSINESS_DIRECTORY) },
                testTag = "category_card_business_directory",
                modifier = Modifier.weight(1f)
            )
            CategoryCardCompact(
                category = PlatformCategory.ADS_PROMOTION,
                icon = Icons.Filled.Campaign,
                accentColor = CrimsonAlert,
                containerColor = CrimsonContainer,
                countBadge = "${uiState.promotions.size} ऑफर • बुकिंग",
                onClick = { onCategoryClick(PlatformCategory.ADS_PROMOTION) },
                testTag = "category_card_ads_promotion",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun CategoryCardWide(
    category: PlatformCategory,
    icon: ImageVector,
    accentColor: Color,
    containerColor: Color,
    countBadge: String,
    onClick: () -> Unit,
    testTag: String
) {
    Surface(
        onClick = onClick,
        color = MaterialTheme.colorScheme.surfaceContainerLowest,
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(1.dp, accentColor.copy(alpha = 0.28f)),
        tonalElevation = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .minimumInteractiveComponentSize()
            .testTag(testTag)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(containerColor)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = category.titleHi,
                    tint = accentColor,
                    modifier = Modifier.size(30.dp)
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = category.titleHi,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    StatusPill(
                        text = countBadge,
                        containerColor = containerColor,
                        contentColor = accentColor
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = category.subtitleHi,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(accentColor.copy(alpha = 0.12f))
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
private fun CategoryCardCompact(
    category: PlatformCategory,
    icon: ImageVector,
    accentColor: Color,
    containerColor: Color,
    countBadge: String,
    onClick: () -> Unit,
    testTag: String,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        color = MaterialTheme.colorScheme.surfaceContainerLowest,
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.55f)),
        tonalElevation = 1.dp,
        modifier = modifier
            .minimumInteractiveComponentSize()
            .testTag(testTag)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(containerColor)
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = category.titleHi,
                        tint = accentColor,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = category.titleHi,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = category.subtitleHi,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            StatusPill(
                text = countBadge,
                containerColor = containerColor,
                contentColor = accentColor
            )
        }
    }
}

@Composable
private fun HighlightQuickCard(
    tag: String,
    title: String,
    subtitle: String,
    accentColor: Color,
    onClick: () -> Unit,
    testTag: String
) {
    Surface(
        onClick = onClick,
        color = MaterialTheme.colorScheme.surfaceContainerLowest,
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
        modifier = Modifier
            .width(265.dp)
            .minimumInteractiveComponentSize()
            .testTag(testTag)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = tag,
                style = MaterialTheme.typography.labelMedium,
                color = accentColor,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
