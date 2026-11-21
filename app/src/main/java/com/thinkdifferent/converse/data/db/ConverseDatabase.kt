package com.thinkdifferent.converse.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.thinkdifferent.converse.data.dao.ConversationDao
import com.thinkdifferent.converse.data.entity.ConversationEntity
import com.thinkdifferent.converse.data.entity.MessageEntity

@Database(
    entities = [ConversationEntity::class, MessageEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ConverseDatabase : RoomDatabase() {
    abstract fun conversationDao(): ConversationDao

    companion object {
        @Volatile
        private var instance: ConverseDatabase? = null

        fun getInstance(context: Context): ConverseDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    ConverseDatabase::class.java,
                    "converse.db"
                ).build().also { instance = it }
            }
    }
}
