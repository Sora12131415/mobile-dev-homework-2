package com.harbourspace.unsplash.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UnsplashDao {
    @Query("SELECT * FROM images")
    fun getAllImages(): Flow<List<UnsplashItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(images: List<UnsplashItem>)

    @Query("DELETE FROM images")
    suspend fun clearAll()
}
