package space.models.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class Character(
    @PrimaryKey
    val id: String,
    val name: String,
    val isSelected: Boolean = false
)