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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.model.GovtJobItem
import com.example.data.model.JobSectionType
import com.example.ui.components.BookmarkIconButton
import com.example.ui.components.CategoryTopBar
import com.example.ui.components.EmptyStateCard
import com.example.ui.components.FilterChipStrip
import com.example.ui.components.StatusPill
import com.example.ui.theme.CrimsonAlert
import com.example.ui.theme.CrimsonContainer
import com.example.ui.theme.RoyalIndigo
import com.example.ui.theme.RoyalIndigoContainer
import com.example.ui.util.IntentHelpers
import com.example.ui.viewmodel.SupaulUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GovtJobsResultsScreen(
    uiState: SupaulUiState,
    onBackClick: () -> Unit,
    onToggleBookmark: (GovtJobItem) -> Unit
) {
    val context = LocalContext.current
    var selectedSection by remember { mutableStateOf(JobSectionType.LATEST_JOB) }
    var selectedQualification by remember { mutableStateOf("सभी") }
    var selectedJob by remember { mutableStateOf<GovtJobItem?>(null) }

    val qualifications = listOf("सभी", "10वीं", "12वीं", "स्नातक", "ITI")

    val filteredJobs = remember(uiState.govtJobs, selectedSection, selectedQualification) {
        uiState.govtJobs.filter { job ->
            val matchesSection = job.sectionType == selectedSection
            val matchesQual = selectedQualification == "सभी" ||
                job.qualification.contains(selectedQualification, ignoreCase = true)
            matchesSection && matchesQual
        }
    }

    Scaffold(
        topBar = {
            CategoryTopBar(
                title = stringResource(R.string.cat_govt_jobs),
                subtitle = "बिहार व केंद्र सरकार भर्ती, रिजल्ट एवं एडमिट कार्ड",
                onBackClick = onBackClick
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
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    val sections = JobSectionType.entries
                    SingleChoiceSegmentedButtonRow(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        sections.forEachIndexed { index, section ->
                            SegmentedButton(
                                selected = selectedSection == section,
                                onClick = { selectedSection = section },
                                shape = SegmentedButtonDefaults.itemShape(
                                    index = index,
                                    count = sections.size
                                ),
                                modifier = Modifier.testTag("jobs_tab_${section.name}")
                            ) {
                                Text(section.labelHi, maxLines = 1)
                            }
                        }
                    }
                }

                item {
                    FilterChipStrip(
                        items = qualifications,
                        selectedItem = selectedQualification,
                        onSelect = { selectedQualification = it },
                        tagPrefix = "job_qual_chip"
                    )
                }

                if (filteredJobs.isEmpty()) {
                    item {
                        EmptyStateCard(
                            icon = Icons.Filled.Work,
                            title = "इस योग्यता में अभी कोई सूची नहीं मिली",
                            description = "अन्य भर्तियां व रिजल्ट देखने के लिए 'सभी' चुनें।"
                        )
                    }
                } else {
                    items(filteredJobs, key = { it.id }) { job ->
                        val isSaved = uiState.bookmarkedKeys.contains(job.id)
                        GovtJobCard(
                            job = job,
                            isSaved = isSaved,
                            onClick = { selectedJob = job },
                            onBookmarkClick = { onToggleBookmark(job) },
                            onShareClick = {
                                IntentHelpers.shareText(
                                    context,
                                    job.title,
                                    "${job.title}\nविभाग: ${job.department}\n${job.keyDateLabel}\nलिंक: ${job.officialUrl}"
                                )
                            }
                        )
                    }
                }
            }
        }
    }

    selectedJob?.let { job ->
        val isSaved = uiState.bookmarkedKeys.contains(job.id)
        ModalBottomSheet(
            onDismissRequest = { selectedJob = null }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    StatusPill(
                        text = job.sectionType.labelHi,
                        containerColor = RoyalIndigoContainer,
                        contentColor = RoyalIndigo
                    )
                    StatusPill(
                        text = job.qualification,
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = job.title,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = job.department,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(12.dp))

                JobDetailRow("पद / स्थिति", job.postsOrStatus)
                JobDetailRow("मुख्य तिथि", job.keyDateLabel)
                JobDetailRow("आयु सीमा", job.ageLimit)
                JobDetailRow("आवेदन शुल्क", job.applicationFee)

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "पात्रता एवं विवरण:",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = job.eligibilityDetails,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(18.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { onToggleBookmark(job) },
                        modifier = Modifier
                            .weight(1f)
                            .minimumInteractiveComponentSize()
                            .testTag("job_detail_bookmark_button")
                    ) {
                        Text(if (isSaved) "सहेजा गया" else "सहेजें")
                    }
                    Button(
                        onClick = { IntentHelpers.openExternalUrl(context, job.officialUrl) },
                        modifier = Modifier
                            .weight(1f)
                            .minimumInteractiveComponentSize()
                            .testTag("job_detail_portal_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("आधिकारिक साइट")
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun JobDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun GovtJobCard(
    job: GovtJobItem,
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
            .testTag("job_card_${job.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    if (job.isUrgent) {
                        StatusPill(
                            text = "शीघ्र / नया",
                            containerColor = CrimsonContainer,
                            contentColor = CrimsonAlert
                        )
                    }
                    StatusPill(
                        text = job.qualification,
                        containerColor = RoyalIndigoContainer,
                        contentColor = RoyalIndigo
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    BookmarkIconButton(
                        isSaved = isSaved,
                        onClick = onBookmarkClick,
                        testTag = "job_bookmark_${job.id}"
                    )
                    IconButton(
                        onClick = onShareClick,
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .testTag("job_share_${job.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = "शेयर करें",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = job.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = job.department,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = job.postsOrStatus,
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = job.keyDateLabel,
                    style = MaterialTheme.typography.labelMedium,
                    color = if (job.isUrgent) CrimsonAlert else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
