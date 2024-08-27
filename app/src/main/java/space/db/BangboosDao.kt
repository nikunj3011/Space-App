package space.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import space.models.db.Bangboo

@Dao
interface BangboosDao {

    @Upsert()
    suspend fun upsertEchoes(categories: List<Bangboo>)

    @Query("SELECT * FROM echos")
    suspend fun findEchos(): List<Bangboo>

    @Query("SELECT * FROM echos")
    fun getEchos(): LiveData<List<Bangboo>>

    @Query("SELECT * FROM echos WHERE name = :name")
    fun getSelectedEcho(name: String): LiveData<Bangboo>

//    @Query("UPDATE echos SET name = 1 WHERE id = :Bangboo")
//    suspend fun setSelectedCategory(Bangboo: String)
//
//    @Query("UPDATE echos SET isSelected = 0 WHERE id != :Bangboo")
//    suspend fun clearSelectedCategory(Bangboo: String)

}