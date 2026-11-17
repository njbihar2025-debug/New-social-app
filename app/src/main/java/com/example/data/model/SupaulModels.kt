package com.example.data.model

enum class PlatformCategory(
    val id: String,
    val titleHi: String,
    val subtitleHi: String,
    val badgeText: String
) {
    LOCAL_NEWS(
        id = "local_news",
        titleHi = "लोकल न्यूज़",
        subtitleHi = "ताज़ा खबरें, प्रखंड अपडेट व मंडी भाव",
        badgeText = "लाइव अपडेट"
    ),
    BANKING_DIGITAL(
        id = "banking_digital",
        titleHi = "बैंकिंग एवं डिजिटल सर्विस",
        subtitleHi = "CSC सेवाएं, आधार-पैन, RTPS व बैंक शाखाएं",
        badgeText = "ऑनलाइन सेवा"
    ),
    GOVT_JOBS_RESULTS(
        id = "govt_jobs",
        titleHi = "सरकारी नौकरी एवं रिजल्ट",
        subtitleHi = "बिहार/केंद्र भर्ती, एडमिट कार्ड व मेरिट लिस्ट",
        badgeText = "नया अलर्ट"
    ),
    BUSINESS_DIRECTORY(
        id = "business_directory",
        titleHi = "लोकल बिजनेस डायरेक्टरी",
        subtitleHi = "दुकान, अस्पताल, कोचिंग, होटल व संपर्क नंबर",
        badgeText = "सत्यापित सूची"
    ),
    ADS_PROMOTION(
        id = "ads_promotion",
        titleHi = "विज्ञापन एवं प्रमोशन",
        subtitleHi = "लोकल ऑफर देखें व अपना विज्ञापन बुक करें",
        badgeText = "व्यापार बढ़ाएं"
    )
}

enum class SavedItemType(val labelHi: String) {
    NEWS("लोकल न्यूज़"),
    JOB("नौकरी / रिजल्ट"),
    BUSINESS("बिजनेस"),
    SERVICE("डिजिटल सेवा")
}

data class NewsItem(
    val id: String,
    val title: String,
    val summary: String,
    val fullContent: String,
    val blockName: String,
    val topicTag: String,
    val timeAgo: String,
    val isBreaking: Boolean = false
)

data class DigitalServiceItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val category: String,
    val requiredDocs: List<String>,
    val processingTime: String,
    val govtFee: String,
    val officialUrl: String,
    val stepsSummary: String
)

data class BankBranchItem(
    val id: String,
    val bankName: String,
    val branchName: String,
    val ifscCode: String,
    val address: String,
    val blockName: String,
    val timings: String,
    val phone: String,
    val hasAtm: Boolean = true
)

enum class JobSectionType(val labelHi: String) {
    LATEST_JOB("नई भर्तियां"),
    RESULT("रिजल्ट"),
    ADMIT_CARD("एडमिट कार्ड")
}

data class GovtJobItem(
    val id: String,
    val title: String,
    val department: String,
    val sectionType: JobSectionType,
    val qualification: String,
    val postsOrStatus: String,
    val keyDateLabel: String,
    val isUrgent: Boolean,
    val eligibilityDetails: String,
    val ageLimit: String,
    val applicationFee: String,
    val officialUrl: String
)

data class BusinessItem(
    val id: String,
    val name: String,
    val category: String,
    val ownerName: String,
    val phone: String,
    val address: String,
    val blockName: String,
    val timings: String,
    val rating: String,
    val isVerified: Boolean,
    val description: String,
    val isUserAdded: Boolean = false
)

data class PromotionOfferItem(
    val id: String,
    val businessName: String,
    val title: String,
    val discountBadge: String,
    val description: String,
    val validTill: String,
    val blockName: String,
    val phone: String
)

data class AdPlan(
    val id: String,
    val title: String,
    val duration: String,
    val price: String,
    val features: List<String>,
    val isPopular: Boolean = false
)

data class HelplineItem(
    val id: String,
    val title: String,
    val number: String,
    val note: String
)
