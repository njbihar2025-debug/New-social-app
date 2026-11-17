package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AddBusiness
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.model.PlatformCategory
import com.example.ui.components.SectionHeader
import com.example.ui.components.StatusPill
import com.example.ui.theme.CrimsonAlert
import com.example.ui.theme.CrimsonContainer
import com.example.ui.theme.MarigoldContainer
import com.example.ui.theme.MarigoldSaffron
import com.example.ui.util.IntentHelpers
import com.example.ui.viewmodel.SupaulUiState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ServicesHubScreen(
    uiState: SupaulUiState,
    onOpenCategory: (PlatformCategory) -> Unit,
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
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Text(
                    text = "सेवाएं जोड़ें एवं जिला सहायता",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "अपना व्यापार जोड़ें, विज्ञापन बुक करें या सुपौल जिला हेल्पलाइन से जुड़ें",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            item {
                QuickActionHubCard(
                    icon = Icons.Filled.Call,
                    title = "ऐप कॉलिंग सुविधा एवं सहायता डेस्क",
                    subtitle = "न्यूज़ डेस्क, विज्ञापन सहायता, हेल्पलाइन या किसी भी नंबर पर तुरंत कॉल करें",
                    badgeText = "1-टैप कॉल",
                    onClick = onCallingClick,
                    testTag = "hub_app_calling_card"
                )
            }

            item {
                QuickActionHubCard(
                    icon = Icons.Filled.AddBusiness,
                    title = "डायरेक्टरी में अपना बिजनेस जोड़ें",
                    subtitle = "दुकान, क्लिनिक, कोचिंग या सेवा को सुपौल के हजारों लोगों तक पहुंचाएं",
                    badgeText = "निःशुल्क लिस्टिंग",
                    onClick = { onOpenCategory(PlatformCategory.BUSINESS_DIRECTORY) },
                    testTag = "hub_add_business_card"
                )
            }

            item {
                QuickActionHubCard(
                    icon = Icons.Filled.Campaign,
                    title = "विज्ञापन एवं प्रमोशन बुक करें",
                    subtitle = "बैनर विज्ञापन, फेस्टिवल ऑफर और लोकल प्रमोशन पैकेज देखें",
                    badgeText = "व्यापार बढ़ाएं",
                    onClick = { onOpenCategory(PlatformCategory.ADS_PROMOTION) },
                    testTag = "hub_book_ad_card"
                )
            }

            item {
                Surface(
                    onClick = { IntentHelpers.openExternalUrl(context, "https://supaul.nic.in/") },
                    color = MaterialTheme.colorScheme.surfaceContainerLowest,
                    shape = MaterialTheme.shapes.large,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.55f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .minimumInteractiveComponentSize()
                        .testTag("hub_supaul_nic_card")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Public,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "सुपौल जिला आधिकारिक वेबसाइट (supaul.nic.in)",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "जिला प्रशासन सूचनाएं, जनप्रतिनिधि व सरकारी निर्देश देखें",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            item {
                SectionHeader(title = "सुपौल जिले के सभी 11 प्रखंड")
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    uiState.blocks.filter { it != "सभी प्रखंड" }.forEach { block ->
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = MaterialTheme.shapes.small
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.LocationCity,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = block,
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                        }
                    }
                }
            }

            item {
                SectionHeader(title = "आपातकालीन संपर्क नंबर")
            }

            items(uiState.helplines, key = { it.id }) { helpline ->
                Surface(
                    onClick = { IntentHelpers.dialPhoneNumber(context, helpline.number) },
                    color = MaterialTheme.colorScheme.surfaceContainerLowest,
                    shape = MaterialTheme.shapes.medium,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .minimumInteractiveComponentSize()
                        .testTag("hub_helpline_${helpline.id}")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(14.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = helpline.title,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = helpline.note,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        StatusPill(
                            text = helpline.number,
                            containerColor = CrimsonContainer,
                            contentColor = CrimsonAlert
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Filled.Call,
                            contentDescription = "कॉल करें",
                            tint = CrimsonAlert
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun QuickActionHubCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    badgeText: String,
    onClick: () -> Unit,
    testTag: String
) {
    Surface(
        onClick = onClick,
        color = MaterialTheme.colorScheme.surfaceContainerLowest,
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.55f)),
        modifier = Modifier
            .fillMaxWidth()
            .minimumInteractiveComponentSize()
            .testTag(testTag)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                StatusPill(
                    text = badgeText,
                    containerColor = MarigoldContainer,
                    contentColor = MarigoldSaffron
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}
