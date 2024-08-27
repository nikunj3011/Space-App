package space.models.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bangboo")
data class Bangboo(
    @PrimaryKey
    val No: String,
    val PossibleSonataEffects: String,
    val classEcho: String,
    val cooldown: String,
    val cost: String,
    val description: String,
    val img: String,
    val name: String
)