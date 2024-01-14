package com.tiooooo.data.movie.implementation.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tiooooo.data.movie.implementation.local.entity.SearchHistoryEntity

@Dao
interface SearchHistoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(searchHistory: SearchHistoryEntity)

    @Update
    fun update(searchHistory: SearchHistoryEntity)

    @Delete
    fun delete(searchHistory: SearchHistoryEntity)

    @Query("SELECT * from SearchHistoryEntity ORDER BY lastUpdated DESC")
    fun getSearchHistory(): LiveData<List<SearchHistoryEntity>>
}
