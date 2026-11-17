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
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Devices
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
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.data.model.BankBranchItem
import com.example.data.model.DigitalServiceItem
import com.example.ui.components.BookmarkIconButton
import com.example.ui.components.CategoryTopBar
import com.example.ui.components.StatusPill
import com.example.ui.theme.MithilaEmerald
import com.example.ui.theme.MithilaEmeraldContainer
import com.example.ui.util.IntentHelpers
import com.example.ui.viewmodel.SupaulUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BankingDigitalScreen(
    uiState: SupaulUiState,
    onBackClick: () -> Unit,
    onToggleServiceBookmark: (DigitalServiceItem) -> Unit,
    onCallingClick: () -> Unit
) {
    val context = LocalContext.current
    var selectedTab by remember { mutableIntStateOf(0) }
    var selectedService by remember { mutableStateOf<DigitalServiceItem?>(null) }

    Scaffold(
        topBar = {
            CategoryTopBar(
                title = stringResource(R.string.cat_banking_digital),
                subtitle = "CSC सेवाएं, सरकारी पोर्टल और सुपौल बैंक शाखाएं",
                onBackClick = onBackClick,
                actions = {
                    IconButton(
                        onClick = onCallingClick,
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .testTag("banking_call_action")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Call,
                            contentDescription = "डिजिटल हेल्पलाइन कॉल",
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
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    SingleChoiceSegmentedButtonRow(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        SegmentedButton(
                            selected = selectedTab == 0,
                            onClick = { selectedTab = 0 },
                            shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
                            icon = { Icon(Icons.Filled.Devices, contentDescription = null) },
                            modifier = Modifier.testTag("banking_tab_digital_services")
                        ) {
                            Text("डिजिटल / CSC सेवाएं")
                        }
                        SegmentedButton(
                            selected = selectedTab == 1,
                            onClick = { selectedTab = 1 },
                            shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
                            icon = { Icon(Icons.Filled.AccountBalance, contentDescription = null) },
                            modifier = Modifier.testTag("banking_tab_bank_branches")
                        ) {
                            Text("बैंक व IFSC सूची")
                        }
                    }
                }

                if (selectedTab == 0) {
                    items(uiState.digitalServices, key = { it.id }) { service ->
                        val isSaved = uiState.bookmarkedKeys.contains(service.id)
                        DigitalServiceCard(
                            service = service,
                            isSaved = isSaved,
                            onClick = { selectedService = service },
                            onBookmarkClick = { onToggleServiceBookmark(service) },
                            onOpenPortal = { IntentHelpers.openExternalUrl(context, service.officialUrl) }
                        )
                    }
                } else {
                    items(uiState.bankBranches, key = { it.id }) { branch ->
                        BankBranchCard(
                            branch = branch,
                            onCopyIfsc = {
                                IntentHelpers.copyToClipboard(context, "IFSC कोड", branch.ifscCode)
                            },
                            onCallClick = {
                                IntentHelpers.dialPhoneNumber(context, branch.phone)
                            }
                        )
                    }
                }
            }
        }
    }

    selectedService?.let { service ->
        ModalBottomSheet(
            onDismissRequest = { selectedService = null }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                StatusPill(
                    text = service.category,
                    containerColor = MithilaEmeraldContainer,
                    contentColor = MithilaEmerald
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = service.title,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = service.subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "आवश्यक दस्तावेज़ (Required Documents):",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                service.requiredDocs.forEach { doc ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 3.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = null,
                            tint = MithilaEmerald,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = doc, style = MaterialTheme.typography.bodyMedium)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "आवेदन प्रक्रिया:",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = service.stepsSummary,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text("समय अवधि", style = MaterialTheme.typography.labelSmall)
                            Text(service.processingTime, style = MaterialTheme.typography.labelLarge)
                        }
                    }
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text("सरकारी शुल्क", style = MaterialTheme.typography.labelSmall)
                            Text(service.govtFee, style = MaterialTheme.typography.labelLarge)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))
                Button(
                    onClick = { IntentHelpers.openExternalUrl(context, service.officialUrl) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .minimumInteractiveComponentSize()
                        .testTag("service_detail_open_portal_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("आधिकारिक सरकारी पोर्टल खोलें")
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun DigitalServiceCard(
    service: DigitalServiceItem,
    isSaved: Boolean,
    onClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onOpenPortal: () -> Unit
) {
    Surface(
        onClick = onClick,
        color = MaterialTheme.colorScheme.surfaceContainerLowest,
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.55f)),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("digital_service_card_${service.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                StatusPill(
                    text = service.category,
                    containerColor = MithilaEmeraldContainer,
                    contentColor = MithilaEmerald
                )
                BookmarkIconButton(
                    isSaved = isSaved,
                    onClick = onBookmarkClick,
                    testTag = "service_bookmark_${service.id}"
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = service.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = service.subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "दस्तावेज़: ${service.requiredDocs.size} | ${service.govtFee}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                OutlinedButton(
                    onClick = onOpenPortal,
                    modifier = Modifier
                        .minimumInteractiveComponentSize()
                        .testTag("service_open_portal_${service.id}")
                ) {
                    Text("पोर्टल खोलें")
                }
            }
        }
    }
}

@Composable
private fun BankBranchCard(
    branch: BankBranchItem,
    onCopyIfsc: () -> Unit,
    onCallClick: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceContainerLowest,
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.55f)),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("bank_branch_card_${branch.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                StatusPill(
                    text = branch.blockName,
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
                if (branch.hasAtm) {
                    StatusPill(
                        text = "ATM उपलब्ध",
                        containerColor = MithilaEmeraldContainer,
                        contentColor = MithilaEmerald
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = branch.bankName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = branch.branchName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "पता: ${branch.address}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "समय: ${branch.timings}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onCopyIfsc,
                    modifier = Modifier
                        .weight(1f)
                        .minimumInteractiveComponentSize()
                        .testTag("copy_ifsc_${branch.id}")
                ) {
                    Icon(
                        imageVector = Icons.Filled.ContentCopy,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(branch.ifscCode)
                }
                Button(
                    onClick = onCallClick,
                    modifier = Modifier
                        .minimumInteractiveComponentSize()
                        .testTag("call_bank_${branch.id}")
                ) {
                    Icon(
                        imageVector = Icons.Filled.Call,
                        contentDescription = "कॉल करें",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("हेल्पलाइन")
                }
            }
        }
    }
}
