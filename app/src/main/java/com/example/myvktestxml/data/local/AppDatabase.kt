package com.example.myvktestxml.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myvktestxml.domain.models.VideoEntity

@Database(entities = [VideoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun videoDao(): VideoDao
}