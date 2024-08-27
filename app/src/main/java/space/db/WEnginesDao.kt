package space.db

import androidx.room.Dao
import androidx.room.Query

@Dao
interface WEnginesDao {

    @Query("UPDATE categories SET isSelected = 1 WHERE id = :categoryId")
    suspend fun setSelectedCategory(categoryId: String)

    @Query("UPDATE categories SET isSelected = 0 WHERE id != :categoryId")
    suspend fun clearSelectedCategory(categoryId: String)

}