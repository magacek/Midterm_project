package com.example.midterm_project
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
/**
 * Data Access Object (DAO) for the `Score` entity.
 *
 * <p>This DAO provides methods for interacting with the `scores` table in the Room database,
 * such as querying all scores, inserting a new score, and deleting a score by its ID.
 * Each method is annotated to describe the SQL operation it represents.</p>
 *
 * Example Usage (assuming you have a `db` instance of `AppDatabase`):
 * <pre>
 * val scores = db.scoreDao().getAllScores()
 * db.scoreDao().insert(Score(playerName = "John", attempts = 5))
 * db.scoreDao().delete(1)
 * </pre>
 *
 * @author Matt Gacek
 * @see androidx.room.Dao
 * @see Score
 */
@Dao
interface ScoreDao {
    @Query("SELECT * FROM scores ORDER BY attempts ASC")
    fun getAllScores(): List<Score>

    @Insert
    fun insert(score: Score)

    @Query("DELETE FROM scores WHERE id = :id")
    fun delete(id: Int)
}
