package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Dialpad
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.data.model.HelplineItem
import com.example.ui.theme.MithilaEmerald
import com.example.ui.theme.MithilaEmeraldContainer
import com.example.ui.util.IntentHelpers

private data class QuickCallOption(
    val id: String,
    val title: String,
    val subtitle: String,
    val number: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppCallingModalSheet(
    helplines: List<HelplineItem>,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var customNumber by remember { mutableStateOf("") }
    var numberError by remember { mutableStateOf<String?>(null) }

    val appSupportDesks = remember {
        listOf(
            QuickCallOption(
                id = "desk_news",
                title = "अपना सुपौल न्यूज़ व रिपोर्टिंग डेस्क",
                subtitle = "लोकल खबर भेजने या सुधार के लिए कॉल करें",
                number = "06473224001"
            ),
            QuickCallOption(
                id = "desk_biz",
                title = "बिजनेस लिस्टिंग व विज्ञापन सहायता",
                subtitle = "दुकान जोड़ने या प्रमोशन पैकेज की जानकारी हेतु",
                number = "9835012340"
            ),
            QuickCallOption(
                id = "desk_elec",
                title = "बिजली शिकायत व स्मार्ट मीटर (NBPDCL)",
                subtitle = "टोल-फ्री ऊर्जा सहायता केंद्र",
                number = "1912"
            ),
            QuickCallOption(
                id = "desk_uidai",
                title = "आधार व डिजिटल सेवा हेल्पलाइन (UIDAI)",
                subtitle = "टोल-फ्री आधार अपडेट सहायता",
                number = "1947"
            )
        )
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = Modifier.testTag("app_calling_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(MithilaEmeraldContainer)
                ) {
                    Icon(
                        imageVector = Icons.Filled.SupportAgent,
                        contentDescription = null,
                        tint = MithilaEmerald,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "ऐप कॉलिंग सुविधा (Calling Hub)",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "एक टैप में सहायता डेस्क, हेल्पलाइन या किसी भी नंबर पर कॉल करें",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Custom Quick Dial Box
            Surface(
                color = MaterialTheme.colorScheme.surfaceContainerLow,
                shape = MaterialTheme.shapes.medium,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.55f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Dialpad,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "तुरंत नंबर डायल करें",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    OutlinedTextField(
                        value = customNumber,
                        onValueChange = {
                            customNumber = it.filter { c -> c.isDigit() || c == '+' }.take(13)
                            numberError = null
                        },
                        placeholder = { Text("मोबाइल या हेल्पलाइन नंबर लिखें…") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("calling_custom_number_input")
                    )
                    if (numberError != null) {
                        Text(
                            text = numberError!!,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    Button(
                        onClick = {
                            if (customNumber.length < 3) {
                                numberError = "कृपया सही फ़ोन या हेल्पलाइन नंबर दर्ज करें।"
                            } else {
                                IntentHelpers.dialPhoneNumber(context, customNumber)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .minimumInteractiveComponentSize()
                            .testTag("calling_dial_custom_button")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Call,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("अभी कॉल करें")
                    }
                }
            }

            HorizontalDivider()

            Text(
                text = "सहायता एवं डिजिटल सेवा कॉल",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )

            appSupportDesks.forEach { desk ->
                CallingOptionRow(
                    title = desk.title,
                    subtitle = desk.subtitle,
                    number = desk.number,
                    onClick = { IntentHelpers.dialPhoneNumber(context, desk.number) },
                    testTag = "calling_desk_${desk.id}"
                )
            }

            HorizontalDivider()

            Text(
                text = "आपातकालीन एवं जिला हेल्पलाइन",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )

            helplines.forEach { helpline ->
                CallingOptionRow(
                    title = helpline.title,
                    subtitle = helpline.note,
                    number = helpline.number,
                    onClick = { IntentHelpers.dialPhoneNumber(context, helpline.number) },
                    testTag = "calling_helpline_${helpline.id}"
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun CallingOptionRow(
    title: String,
    subtitle: String,
    number: String,
    onClick: () -> Unit,
    testTag: String
) {
    Surface(
        onClick = onClick,
        color = MaterialTheme.colorScheme.surfaceContainerLowest,
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
        modifier = Modifier
            .fillMaxWidth()
            .minimumInteractiveComponentSize()
            .testTag(testTag)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            StatusPill(
                text = number,
                containerColor = MithilaEmeraldContainer,
                contentColor = MithilaEmerald
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Filled.Call,
                contentDescription = "कॉल करें",
                tint = MithilaEmerald,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
