package com.example.ui.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

object IntentHelpers {

    fun dialPhoneNumber(context: Context, phoneNumber: String) {
        val cleanNumber = phoneNumber.filter { it.isDigit() || it == '+' }
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$cleanNumber")
        }
        runCatching {
            context.startActivity(intent)
        }.onFailure {
            Toast.makeText(context, "फ़ोन डायलर उपलब्ध नहीं है: $phoneNumber", Toast.LENGTH_SHORT).show()
        }
    }

    fun openExternalUrl(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        runCatching {
            context.startActivity(intent)
        }.onFailure {
            Toast.makeText(context, "पोर्टल खोलने में समस्या आई", Toast.LENGTH_SHORT).show()
        }
    }

    fun shareText(context: Context, subject: String, text: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, "$text\n\n— APNA SUPAUL (आपका अपना लोकल डिजिटल प्लेटफॉर्म)")
        }
        runCatching {
            context.startActivity(Intent.createChooser(intent, "शेयर करें"))
        }
    }

    fun copyToClipboard(context: Context, label: String, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
        clipboard?.setPrimaryClip(ClipData.newPlainText(label, text))
        Toast.makeText(context, "$label कॉपी हो गया: $text", Toast.LENGTH_SHORT).show()
    }
}
