package com.example.midterm_project
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ScoreDao {
    @Query("SELECT * FROM scores ORDER BY attempts ASC")
    fun getAllScores(): List<Score>

    @Insert
    fun insert(score: Score)

    @Query("DELETE FROM scores WHERE id = :id")
    fun delete(id: Int)
}
