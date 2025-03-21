package com.example.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.persistence.model.ImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {
    @Query("SELECT * FROM ImageEntity WHERE id = :id")
    suspend fun getById(id: Int): ImageEntity

    @Query("SELECT * FROM ImageEntity WHERE user_id = :userId")
    fun observeByUserId(userId: String): Flow<List<ImageEntity>>

    @Insert
    suspend fun insertAll(image: List<ImageEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(image: ImageEntity)

    @Query("UPDATE ImageEntity SET score = :score WHERE id = :imageId")
    suspend fun updateScoreById(imageId: Int, score: Int)

    @Query("DELETE FROM ImageEntity WHERE id = :imageId")
    suspend fun deleteById(imageId: Int)

    @Query("DELETE FROM ImageEntity WHERE user_id = :userId")
    suspend fun deleteAllByUserId(userId: String)

    @Query("SELECT * FROM ImageEntity WHERE user_id = :userId ORDER BY score DESC LIMIT 1")
    fun observePersonalBestScore(userId: String): Flow<ImageEntity?>
}
