package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.SupaulRepository
import com.example.data.local.AdInquiryEntity
import com.example.data.local.BookmarkEntity
import com.example.data.local.SupaulDatabase
import com.example.data.model.AdPlan
import com.example.data.model.BankBranchItem
import com.example.data.model.BusinessItem
import com.example.data.model.DigitalServiceItem
import com.example.data.model.GovtJobItem
import com.example.data.model.HelplineItem
import com.example.data.model.NewsItem
import com.example.data.model.PromotionOfferItem
import com.example.data.model.SavedItemType
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class SupaulUiState(
    val selectedBlock: String = "सभी प्रखंड",
    val blocks: List<String> = emptyList(),
    val news: List<NewsItem> = emptyList(),
    val digitalServices: List<DigitalServiceItem> = emptyList(),
    val bankBranches: List<BankBranchItem> = emptyList(),
    val govtJobs: List<GovtJobItem> = emptyList(),
    val businesses: List<BusinessItem> = emptyList(),
    val promotions: List<PromotionOfferItem> = emptyList(),
    val adPlans: List<AdPlan> = emptyList(),
    val helplines: List<HelplineItem> = emptyList(),
    val bookmarks: List<BookmarkEntity> = emptyList(),
    val bookmarkedKeys: Set<String> = emptySet(),
    val adInquiries: List<AdInquiryEntity> = emptyList()
)

class SupaulViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: SupaulRepository by lazy {
        val db = SupaulDatabase.getInstance(application)
        SupaulRepository(db.supaulDao())
    }

    private val _selectedBlock = MutableStateFlow("सभी प्रखंड")
    val selectedBlock: StateFlow<String> = _selectedBlock.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _uiMessages = MutableSharedFlow<String>()
    val uiMessages: SharedFlow<String> = _uiMessages.asSharedFlow()

    val uiState: StateFlow<SupaulUiState> = combine(
        _selectedBlock,
        repository.bookmarks,
        repository.bookmarkedKeys,
        repository.allBusinesses,
        repository.adInquiries
    ) { block, bookmarks, keys, businesses, inquiries ->
        SupaulUiState(
            selectedBlock = block,
            blocks = repository.getBlocks(),
            news = repository.getNews(),
            digitalServices = repository.getDigitalServices(),
            bankBranches = repository.getBankBranches(),
            govtJobs = repository.getGovtJobs(),
            businesses = businesses,
            promotions = repository.getPromotions(),
            adPlans = repository.getAdPlans(),
            helplines = repository.getHelplines(),
            bookmarks = bookmarks,
            bookmarkedKeys = keys,
            adInquiries = inquiries
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SupaulUiState(
            blocks = repository.getBlocks(),
            news = repository.getNews(),
            digitalServices = repository.getDigitalServices(),
            bankBranches = repository.getBankBranches(),
            govtJobs = repository.getGovtJobs(),
            promotions = repository.getPromotions(),
            adPlans = repository.getAdPlans(),
            helplines = repository.getHelplines()
        )
    )

    fun selectBlock(block: String) {
        _selectedBlock.value = block
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun toggleBookmark(
        itemKey: String,
        itemType: SavedItemType,
        title: String,
        subtitle: String
    ) {
        viewModelScope.launch {
            val currentlySaved = uiState.value.bookmarkedKeys.contains(itemKey)
            repository.toggleBookmark(
                itemKey = itemKey,
                itemType = itemType,
                title = title,
                subtitle = subtitle,
                currentlySaved = currentlySaved
            )
            _uiMessages.emit(
                if (currentlySaved) "बुकमार्क से हटाया गया" else "सहेजे गए में जोड़ा गया"
            )
        }
    }

    fun removeBookmark(itemKey: String) {
        viewModelScope.launch {
            repository.removeBookmark(itemKey)
            _uiMessages.emit("सहेजे गए आइटम से हटाया गया")
        }
    }

    fun addBusinessListing(
        name: String,
        category: String,
        ownerName: String,
        phone: String,
        address: String,
        blockName: String,
        timings: String,
        description: String
    ) {
        viewModelScope.launch {
            repository.addBusiness(
                name = name,
                category = category,
                ownerName = ownerName,
                phone = phone,
                address = address,
                blockName = blockName,
                timings = timings,
                description = description
            )
            _uiMessages.emit("आपका बिजनेस डायरेक्टरी में सफलतापूर्वक जुड़ गया!")
        }
    }

    fun submitAdInquiry(
        businessName: String,
        contactName: String,
        phone: String,
        category: String,
        blockName: String,
        planTitle: String,
        message: String
    ) {
        viewModelScope.launch {
            val refCode = repository.submitAdInquiry(
                businessName = businessName,
                contactName = contactName,
                phone = phone,
                category = category,
                blockName = blockName,
                planTitle = planTitle,
                message = message
            )
            _uiMessages.emit("विज्ञापन अनुरोध दर्ज हुआ! संदर्भ संख्या: $refCode")
        }
    }

    fun deleteAdInquiry(id: Int) {
        viewModelScope.launch {
            repository.deleteAdInquiry(id)
            _uiMessages.emit("विज्ञापन अनुरोध हटाया गया")
        }
    }
}
