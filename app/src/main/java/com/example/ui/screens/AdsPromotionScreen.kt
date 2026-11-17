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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.local.AdInquiryEntity
import com.example.data.model.AdPlan
import com.example.data.model.PromotionOfferItem
import com.example.ui.components.CategoryTopBar
import com.example.ui.components.FilterChipStrip
import com.example.ui.components.SectionHeader
import com.example.ui.components.StatusPill
import com.example.ui.theme.CrimsonAlert
import com.example.ui.theme.CrimsonContainer
import com.example.ui.theme.MarigoldContainer
import com.example.ui.theme.MarigoldSaffron
import com.example.ui.theme.MithilaEmerald
import com.example.ui.theme.MithilaEmeraldContainer
import com.example.ui.util.IntentHelpers
import com.example.ui.viewmodel.SupaulUiState

@Composable
fun AdsPromotionScreen(
    uiState: SupaulUiState,
    onBackClick: () -> Unit,
    onSubmitAdInquiry: (
        businessName: String,
        contactName: String,
        phone: String,
        category: String,
        blockName: String,
        planTitle: String,
        message: String
    ) -> Unit,
    onDeleteAdInquiry: (Int) -> Unit,
    onCallingClick: () -> Unit
) {
    val context = LocalContext.current
    val blocks = remember(uiState.blocks) { uiState.blocks.filter { it != "सभी प्रखंड" } }

    var selectedPlanTitle by remember {
        mutableStateOf(uiState.adPlans.firstOrNull { it.isPopular }?.title ?: "सुपौल होम बैनर पैक")
    }
    var businessName by remember { mutableStateOf("") }
    var contactName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("दुकान / शोरूम") }
    var blockName by remember { mutableStateOf(blocks.firstOrNull() ?: "सुपौल सदर") }
    var adMessage by remember { mutableStateOf("") }
    var formError by remember { mutableStateOf<String?>(null) }

    val adCategories = listOf("दुकान / शोरूम", "कोचिंग / स्कूल", "अस्पताल / क्लिनिक", "रियल एस्टेट", "इवेंट / सेल")

    Scaffold(
        topBar = {
            CategoryTopBar(
                title = stringResource(R.string.cat_ads_promo),
                subtitle = "लोकल ऑफर देखें व अपना व्यापार प्रमोट करें",
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
                    .widthIn(max = 720.dp)
                    .testTag("ads_promo_list"),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Hero Promo Banner
                item {
                    Card(
                        shape = MaterialTheme.shapes.large,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(145.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_promo_banner),
                                contentDescription = "विज्ञापन एवं प्रमोशन बैनर",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.horizontalGradient(
                                            colors = listOf(
                                                Color(0xE03E1800),
                                                Color(0x993E1800),
                                                Color(0x26000000)
                                            )
                                        )
                                    )
                            )
                            Column(
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(16.dp)
                            ) {
                                StatusPill(
                                    text = "अपने व्यापार को पूरे सुपौल तक पहुंचाएं",
                                    containerColor = MarigoldSaffron,
                                    contentColor = Color.White
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "आकर्षक लोकल डील, सेल और ब्रांड प्रमोशन",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }

                // Active Local Offers
                item {
                    SectionHeader(title = "सुपौल के सक्रिय ऑफर एवं डील")
                }

                item {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(uiState.promotions, key = { it.id }) { offer ->
                            PromoOfferCard(
                                offer = offer,
                                onCallClick = { IntentHelpers.dialPhoneNumber(context, offer.phone) }
                            )
                        }
                    }
                }

                // Ad Plans
                item {
                    SectionHeader(title = "विज्ञापन पैकेज चुनें")
                }

                item {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(uiState.adPlans, key = { it.id }) { plan ->
                            AdPlanCard(
                                plan = plan,
                                isSelected = selectedPlanTitle == plan.title,
                                onSelect = { selectedPlanTitle = plan.title }
                            )
                        }
                    }
                }

                // Booking Form
                item {
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceContainerLowest,
                        shape = MaterialTheme.shapes.large,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Filled.Campaign,
                                    contentDescription = null,
                                    tint = CrimsonAlert
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "विज्ञापन बुक करें ($selectedPlanTitle)",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            OutlinedTextField(
                                value = businessName,
                                onValueChange = { businessName = it; formError = null },
                                label = { Text("दुकान / संस्था का नाम *") },
                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("ad_business_name_input")
                            )

                            OutlinedTextField(
                                value = contactName,
                                onValueChange = { contactName = it; formError = null },
                                label = { Text("संपर्क व्यक्ति का नाम *") },
                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("ad_contact_name_input")
                            )

                            OutlinedTextField(
                                value = phone,
                                onValueChange = { phone = it.filter { c -> c.isDigit() }.take(10); formError = null },
                                label = { Text("मोबाइल / व्हाट्सएप नंबर *") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("ad_phone_input")
                            )

                            Text("व्यापार श्रेणी:", style = MaterialTheme.typography.labelLarge)
                            FilterChipStrip(
                                items = adCategories,
                                selectedItem = category,
                                onSelect = { category = it },
                                tagPrefix = "ad_cat_chip"
                            )

                            Text("प्रखंड:", style = MaterialTheme.typography.labelLarge)
                            FilterChipStrip(
                                items = blocks,
                                selectedItem = blockName,
                                onSelect = { blockName = it },
                                tagPrefix = "ad_block_chip"
                            )

                            OutlinedTextField(
                                value = adMessage,
                                onValueChange = { adMessage = it },
                                label = { Text("ऑफर या विज्ञापन संदेश (जैसे: 20% छूट, नया शोरूम)") },
                                minLines = 2,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("ad_message_input")
                            )

                            if (formError != null) {
                                Text(
                                    text = formError!!,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }

                            Button(
                                onClick = {
                                    when {
                                        businessName.isBlank() -> formError = "कृपया दुकान/संस्था का नाम लिखें।"
                                        contactName.isBlank() -> formError = "कृपया अपना नाम लिखें।"
                                        phone.length < 10 -> formError = "कृपया 10 अंकों का मोबाइल नंबर लिखें।"
                                        else -> {
                                            onSubmitAdInquiry(
                                                businessName,
                                                contactName,
                                                phone,
                                                category,
                                                blockName,
                                                selectedPlanTitle,
                                                adMessage.ifBlank { "$businessName — प्रमोशन अनुरोध" }
                                            )
                                            businessName = ""
                                            contactName = ""
                                            phone = ""
                                            adMessage = ""
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .minimumInteractiveComponentSize()
                                    .testTag("submit_ad_inquiry_button")
                            ) {
                                Text("विज्ञापन अनुरोध सबमिट करें")
                            }

                            OutlinedButton(
                                onClick = onCallingClick,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .minimumInteractiveComponentSize()
                                    .testTag("ads_direct_call_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Call,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("सीधे कॉल द्वारा विज्ञापन बुक करें")
                            }
                        }
                    }
                }

                // Submitted Ad Requests List
                if (uiState.adInquiries.isNotEmpty()) {
                    item {
                        SectionHeader(title = "आपके सबमिट किए गए विज्ञापन अनुरोध")
                    }
                    items(uiState.adInquiries, key = { it.id }) { inquiry ->
                        SubmittedAdInquiryCard(
                            inquiry = inquiry,
                            onDelete = { onDeleteAdInquiry(inquiry.id) }
                        )
                    }
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}

@Composable
private fun PromoOfferCard(
    offer: PromotionOfferItem,
    onCallClick: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceContainerLowest,
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(1.dp, MarigoldSaffron.copy(alpha = 0.4f)),
        modifier = Modifier
            .width(275.dp)
            .testTag("promo_offer_card_${offer.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                StatusPill(
                    text = offer.discountBadge,
                    containerColor = CrimsonContainer,
                    contentColor = CrimsonAlert
                )
                Text(
                    text = offer.blockName,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = offer.businessName,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = offer.title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = offer.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = offer.validTill,
                    style = MaterialTheme.typography.labelSmall,
                    color = MarigoldSaffron
                )
                Button(
                    onClick = onCallClick,
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier
                        .minimumInteractiveComponentSize()
                        .testTag("promo_call_${offer.id}")
                ) {
                    Icon(
                        imageVector = Icons.Filled.Call,
                        contentDescription = null,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("संपर्क करें")
                }
            }
        }
    }
}

@Composable
private fun AdPlanCard(
    plan: AdPlan,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Surface(
        onClick = onSelect,
        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceContainerLowest,
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.55f)
        ),
        modifier = Modifier
            .width(235.dp)
            .testTag("ad_plan_card_${plan.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = plan.duration,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                if (plan.isPopular) {
                    StatusPill(
                        text = "लोकप्रिय",
                        containerColor = MarigoldContainer,
                        contentColor = MarigoldSaffron
                    )
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = plan.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = plan.price,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            plan.features.forEach { feature ->
                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = null,
                        tint = MithilaEmerald,
                        modifier = Modifier
                            .padding(top = 2.dp)
                            .size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = feature,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
private fun SubmittedAdInquiryCard(
    inquiry: AdInquiryEntity,
    onDelete: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceContainerLowest,
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(1.dp, MithilaEmerald.copy(alpha = 0.45f)),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("submitted_ad_card_${inquiry.id}")
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(14.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.LocalOffer,
                contentDescription = null,
                tint = MithilaEmerald,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = inquiry.businessName,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    StatusPill(
                        text = inquiry.referenceCode,
                        containerColor = MithilaEmeraldContainer,
                        contentColor = MithilaEmerald
                    )
                }
                Text(
                    text = "${inquiry.planTitle} • ${inquiry.blockName} • ${inquiry.phone}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "स्थिति: ${inquiry.status}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MithilaEmerald
                )
            }
            IconButton(
                onClick = onDelete,
                modifier = Modifier
                    .minimumInteractiveComponentSize()
                    .testTag("delete_ad_inquiry_${inquiry.id}")
            ) {
                Icon(
                    imageVector = Icons.Filled.DeleteOutline,
                    contentDescription = "अनुरोध हटाएं",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
