package com.tiooooo.data.movie.implementation.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchHistoryEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo("query") val query: String,
    @ColumnInfo("lastUpdated") val lastUpdated: Long? = System.currentTimeMillis(),
)
