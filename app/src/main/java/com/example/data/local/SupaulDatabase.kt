package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        BookmarkEntity::class,
        UserBusinessEntity::class,
        AdInquiryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class SupaulDatabase : RoomDatabase() {
    abstract fun supaulDao(): SupaulDao

    companion object {
        @Volatile
        private var INSTANCE: SupaulDatabase? = null

        fun getInstance(context: Context): SupaulDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    SupaulDatabase::class.java,
                    "apna_supaul_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
