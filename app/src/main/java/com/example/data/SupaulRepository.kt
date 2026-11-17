package com.example.data

import com.example.data.local.AdInquiryEntity
import com.example.data.local.BookmarkEntity
import com.example.data.local.SupaulDao
import com.example.data.local.UserBusinessEntity
import com.example.data.model.AdPlan
import com.example.data.model.BankBranchItem
import com.example.data.model.BusinessItem
import com.example.data.model.DigitalServiceItem
import com.example.data.model.GovtJobItem
import com.example.data.model.HelplineItem
import com.example.data.model.NewsItem
import com.example.data.model.PromotionOfferItem
import com.example.data.model.SavedItemType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SupaulRepository(private val dao: SupaulDao) {

    val bookmarks: Flow<List<BookmarkEntity>> = dao.getAllBookmarks()

    val bookmarkedKeys: Flow<Set<String>> = dao.getAllBookmarks()
        .map { list -> list.map { it.itemKey }.toSet() }

    val allBusinesses: Flow<List<BusinessItem>> = dao.getUserBusinesses()
        .map { userEntities ->
            val mappedUser = userEntities.map { entity ->
                BusinessItem(
                    id = "user_biz_${entity.id}",
                    name = entity.name,
                    category = entity.category,
                    ownerName = entity.ownerName,
                    phone = entity.phone,
                    address = entity.address,
                    blockName = entity.blockName,
                    timings = entity.timings.ifBlank { "सुबह 9:00 - शाम 8:00" },
                    rating = "नया",
                    isVerified = false,
                    description = entity.description,
                    isUserAdded = true
                )
            }
            mappedUser + SupaulSeedData.initialBusinesses
        }

    val adInquiries: Flow<List<AdInquiryEntity>> = dao.getAdInquiries()

    fun getNews(): List<NewsItem> = SupaulSeedData.newsItems

    fun getDigitalServices(): List<DigitalServiceItem> = SupaulSeedData.digitalServices

    fun getBankBranches(): List<BankBranchItem> = SupaulSeedData.bankBranches

    fun getGovtJobs(): List<GovtJobItem> = SupaulSeedData.govtJobs

    fun getPromotions(): List<PromotionOfferItem> = SupaulSeedData.promotionOffers

    fun getAdPlans(): List<AdPlan> = SupaulSeedData.adPlans

    fun getHelplines(): List<HelplineItem> = SupaulSeedData.helplines

    fun getBlocks(): List<String> = SupaulSeedData.supaulBlocks

    suspend fun toggleBookmark(
        itemKey: String,
        itemType: SavedItemType,
        title: String,
        subtitle: String,
        currentlySaved: Boolean
    ) {
        if (currentlySaved) {
            dao.deleteBookmarkByKey(itemKey)
        } else {
            dao.insertBookmark(
                BookmarkEntity(
                    itemKey = itemKey,
                    itemType = itemType.name,
                    title = title,
                    subtitle = subtitle
                )
            )
        }
    }

    suspend fun removeBookmark(itemKey: String) {
        dao.deleteBookmarkByKey(itemKey)
    }

    suspend fun addBusiness(
        name: String,
        category: String,
        ownerName: String,
        phone: String,
        address: String,
        blockName: String,
        timings: String,
        description: String
    ) {
        dao.insertUserBusiness(
            UserBusinessEntity(
                name = name.trim(),
                category = category.trim(),
                ownerName = ownerName.trim(),
                phone = phone.trim(),
                address = address.trim(),
                blockName = blockName.trim(),
                timings = timings.trim(),
                description = description.trim()
            )
        )
    }

    suspend fun submitAdInquiry(
        businessName: String,
        contactName: String,
        phone: String,
        category: String,
        blockName: String,
        planTitle: String,
        message: String
    ): String {
        val refCode = "SUP-${(1000..9999).random()}"
        dao.insertAdInquiry(
            AdInquiryEntity(
                referenceCode = refCode,
                businessName = businessName.trim(),
                contactName = contactName.trim(),
                phone = phone.trim(),
                category = category.trim(),
                blockName = blockName.trim(),
                planTitle = planTitle.trim(),
                message = message.trim(),
                status = "सबमिट (समीक्षाधीन)"
            )
        )
        return refCode
    }

    suspend fun deleteAdInquiry(id: Int) {
        dao.deleteAdInquiry(id)
    }
}
