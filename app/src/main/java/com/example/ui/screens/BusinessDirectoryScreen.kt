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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBusiness
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.model.BusinessItem
import com.example.ui.components.BookmarkIconButton
import com.example.ui.components.CategoryTopBar
import com.example.ui.components.EmptyStateCard
import com.example.ui.components.FilterChipStrip
import com.example.ui.components.StatusPill
import com.example.ui.theme.MarigoldContainer
import com.example.ui.theme.MarigoldSaffron
import com.example.ui.theme.MithilaEmerald
import com.example.ui.theme.MithilaEmeraldContainer
import com.example.ui.util.IntentHelpers
import com.example.ui.viewmodel.SupaulUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessDirectoryScreen(
    uiState: SupaulUiState,
    onBackClick: () -> Unit,
    onSelectBlock: (String) -> Unit,
    onToggleBookmark: (BusinessItem) -> Unit,
    onAddBusiness: (
        name: String,
        category: String,
        ownerName: String,
        phone: String,
        address: String,
        blockName: String,
        timings: String,
        description: String
    ) -> Unit
) {
    val context = LocalContext.current
    var searchText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("सभी") }
    var showAddSheet by remember { mutableStateOf(false) }

    val categories = remember(uiState.businesses) {
        listOf("सभी") + uiState.businesses.map { it.category }.distinct()
    }

    val filteredBusinesses = remember(
        uiState.businesses,
        uiState.selectedBlock,
        selectedCategory,
        searchText
    ) {
        uiState.businesses.filter { biz ->
            val matchesBlock = uiState.selectedBlock == "सभी प्रखंड" || biz.blockName == uiState.selectedBlock
            val matchesCat = selectedCategory == "सभी" || biz.category == selectedCategory
            val q = searchText.trim()
            val matchesQuery = q.isBlank() ||
                biz.name.contains(q, ignoreCase = true) ||
                biz.category.contains(q, ignoreCase = true) ||
                biz.address.contains(q, ignoreCase = true) ||
                biz.description.contains(q, ignoreCase = true)
            matchesBlock && matchesCat && matchesQuery
        }
    }

    Scaffold(
        topBar = {
            CategoryTopBar(
                title = stringResource(R.string.cat_business_dir),
                subtitle = "सुपौल जिले की दुकानें, अस्पताल, कोचिंग व सेवाएं",
                onBackClick = onBackClick
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showAddSheet = true },
                icon = { Icon(Icons.Filled.AddBusiness, contentDescription = null) },
                text = { Text("बिजनेस जोड़ें") },
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.testTag("add_business_fab")
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
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = 12.dp,
                    bottom = 88.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    OutlinedTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                        placeholder = { Text("दुकान, डॉक्टर, कोचिंग या पता खोजें…") },
                        singleLine = true,
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("business_search_input")
                    )
                }

                item {
                    FilterChipStrip(
                        items = categories,
                        selectedItem = selectedCategory,
                        onSelect = { selectedCategory = it },
                        tagPrefix = "biz_cat_chip"
                    )
                }

                item {
                    FilterChipStrip(
                        items = uiState.blocks,
                        selectedItem = uiState.selectedBlock,
                        onSelect = onSelectBlock,
                        tagPrefix = "biz_block_chip"
                    )
                }

                if (filteredBusinesses.isEmpty()) {
                    item {
                        EmptyStateCard(
                            icon = Icons.Filled.Storefront,
                            title = "कोई बिजनेस लिस्टिंग नहीं मिली",
                            description = "कृपया फ़िल्टर बदलें या 'बिजनेस जोड़ें' बटन से अपना प्रतिष्ठान जोड़ें।"
                        )
                    }
                } else {
                    items(filteredBusinesses, key = { it.id }) { business ->
                        val isSaved = uiState.bookmarkedKeys.contains(business.id)
                        BusinessDirectoryCard(
                            business = business,
                            isSaved = isSaved,
                            onBookmarkClick = { onToggleBookmark(business) },
                            onCallClick = { IntentHelpers.dialPhoneNumber(context, business.phone) },
                            onShareClick = {
                                IntentHelpers.shareText(
                                    context,
                                    business.name,
                                    "${business.name} (${business.category})\nपता: ${business.address}, ${business.blockName}\nसंपर्क: ${business.phone}"
                                )
                            }
                        )
                    }
                }
            }
        }
    }

    if (showAddSheet) {
        AddBusinessModalSheet(
            blocks = uiState.blocks.filter { it != "सभी प्रखंड" },
            onDismiss = { showAddSheet = false },
            onSubmit = { name, category, owner, phone, address, block, timings, desc ->
                onAddBusiness(name, category, owner, phone, address, block, timings, desc)
                showAddSheet = false
            }
        )
    }
}

@Composable
private fun BusinessDirectoryCard(
    business: BusinessItem,
    isSaved: Boolean,
    onBookmarkClick: () -> Unit,
    onCallClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceContainerLowest,
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.55f)),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("business_card_${business.id}")
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
                    StatusPill(
                        text = business.category,
                        containerColor = MarigoldContainer,
                        contentColor = MarigoldSaffron
                    )
                    if (business.isVerified) {
                        StatusPill(
                            text = "सत्यापित",
                            containerColor = MithilaEmeraldContainer,
                            contentColor = MithilaEmerald
                        )
                    } else if (business.isUserAdded) {
                        StatusPill(
                            text = "आपका जुड़ा हुआ",
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
                BookmarkIconButton(
                    isSaved = isSaved,
                    onClick = onBookmarkClick,
                    testTag = "business_bookmark_${business.id}"
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = business.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                if (business.isVerified) {
                    Icon(
                        imageVector = Icons.Filled.Verified,
                        contentDescription = "सत्यापित प्रतिष्ठान",
                        tint = MithilaEmerald,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = business.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${business.address} (${business.blockName})",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Schedule,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = business.timings,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = MarigoldSaffron,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = business.rating,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onCallClick,
                    modifier = Modifier
                        .weight(1f)
                        .minimumInteractiveComponentSize()
                        .testTag("business_call_${business.id}")
                ) {
                    Icon(
                        imageVector = Icons.Filled.Call,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("कॉल करें")
                }
                OutlinedButton(
                    onClick = onShareClick,
                    modifier = Modifier
                        .minimumInteractiveComponentSize()
                        .testTag("business_share_${business.id}")
                ) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "शेयर करें",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("शेयर")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBusinessModalSheet(
    blocks: List<String>,
    onDismiss: () -> Unit,
    onSubmit: (
        name: String,
        category: String,
        ownerName: String,
        phone: String,
        address: String,
        blockName: String,
        timings: String,
        description: String
    ) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("किराना / कृषि मार्ट") }
    var ownerName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var blockName by remember { mutableStateOf(blocks.firstOrNull() ?: "सुपौल सदर") }
    var timings by remember { mutableStateOf("सुबह 9:00 - शाम 8:00") }
    var description by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    val quickCategories = listOf(
        "किराना / कृषि मार्ट",
        "स्वास्थ्य / अस्पताल",
        "शिक्षा / कोचिंग",
        "डिजिटल व CSC केंद्र",
        "इलेक्ट्रॉनिक्स / मोबाइल",
        "होटल / रेस्टोरेंट"
    )

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "लोकल बिजनेस डायरेक्टरी में जोड़ें",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "अपनी दुकान, क्लिनिक, कोचिंग या सेवा की जानकारी जोड़ें।",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it; errorMsg = null },
                label = { Text("व्यापार / दुकान का नाम *") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("add_biz_name_input")
            )

            Text("श्रेणी चुनें:", style = MaterialTheme.typography.labelLarge)
            FilterChipStrip(
                items = quickCategories,
                selectedItem = category,
                onSelect = { category = it },
                tagPrefix = "add_biz_cat"
            )

            Text("प्रखंड चुनें:", style = MaterialTheme.typography.labelLarge)
            FilterChipStrip(
                items = blocks,
                selectedItem = blockName,
                onSelect = { blockName = it },
                tagPrefix = "add_biz_block"
            )

            OutlinedTextField(
                value = ownerName,
                onValueChange = { ownerName = it },
                label = { Text("संचालक का नाम") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("add_biz_owner_input")
            )

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it.filter { ch -> ch.isDigit() }.take(10); errorMsg = null },
                label = { Text("मोबाइल नंबर (10 अंक) *") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("add_biz_phone_input")
            )

            OutlinedTextField(
                value = address,
                onValueChange = { address = it; errorMsg = null },
                label = { Text("पूरा पता / चौक *") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("add_biz_address_input")
            )

            OutlinedTextField(
                value = timings,
                onValueChange = { timings = it },
                label = { Text("खुलने का समय") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("add_biz_timings_input")
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("सेवाओं का संक्षिप्त विवरण") },
                minLines = 2,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("add_biz_desc_input")
            )

            if (errorMsg != null) {
                Text(
                    text = errorMsg!!,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Button(
                onClick = {
                    when {
                        name.isBlank() -> errorMsg = "कृपया दुकान या व्यापार का नाम दर्ज करें।"
                        phone.length < 10 -> errorMsg = "कृपया 10 अंकों का सही मोबाइल नंबर दर्ज करें।"
                        address.isBlank() -> errorMsg = "कृपया पता दर्ज करें।"
                        else -> onSubmit(
                            name,
                            category,
                            ownerName.ifBlank { "स्थानीय व्यवसायी" },
                            phone,
                            address,
                            blockName,
                            timings,
                            description.ifBlank { "$category सेवा — $blockName, सुपौल" }
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .minimumInteractiveComponentSize()
                    .testTag("submit_new_business_button")
            ) {
                Text("डायरेक्टरी में सेव करें")
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
