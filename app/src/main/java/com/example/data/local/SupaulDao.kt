package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SupaulDao {
    @Query("SELECT * FROM bookmarks ORDER BY savedAt DESC")
    fun getAllBookmarks(): Flow<List<BookmarkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity)

    @Query("DELETE FROM bookmarks WHERE itemKey = :itemKey")
    suspend fun deleteBookmarkByKey(itemKey: String)

    @Query("SELECT * FROM user_businesses ORDER BY createdAt DESC")
    fun getUserBusinesses(): Flow<List<UserBusinessEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserBusiness(business: UserBusinessEntity)

    @Query("DELETE FROM user_businesses WHERE id = :id")
    suspend fun deleteUserBusiness(id: Int)

    @Query("SELECT * FROM ad_inquiries ORDER BY createdAt DESC")
    fun getAdInquiries(): Flow<List<AdInquiryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAdInquiry(inquiry: AdInquiryEntity)

    @Query("DELETE FROM ad_inquiries WHERE id = :id")
    suspend fun deleteAdInquiry(id: Int)
}
