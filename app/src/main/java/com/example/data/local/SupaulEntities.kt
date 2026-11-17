package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey val itemKey: String,
    val itemType: String,
    val title: String,
    val subtitle: String,
    val savedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "user_businesses")
data class UserBusinessEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val category: String,
    val ownerName: String,
    val phone: String,
    val address: String,
    val blockName: String,
    val timings: String,
    val description: String,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "ad_inquiries")
data class AdInquiryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val referenceCode: String,
    val businessName: String,
    val contactName: String,
    val phone: String,
    val category: String,
    val blockName: String,
    val planTitle: String,
    val message: String,
    val status: String,
    val createdAt: Long = System.currentTimeMillis()
)
