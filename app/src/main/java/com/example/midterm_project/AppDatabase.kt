package com.example.midterm_project

import androidx.room.Database
import androidx.room.RoomDatabase
/**
 * Represents the main database for the application using the Room persistence library.
 * Currently, it holds the `Score` entity.
 *
 * <p>To get an instance of the database, you would typically use Room's database builder
 * and then you can access each DAO (Data Access Object) through the provided methods.</p>
 *
 * <p>For example, to access the `ScoreDao`:</p>
 * <pre>
 * val db: AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "app-db").build()
 * val scoreDao: ScoreDao = db.scoreDao()
 * </pre>
 *
 * @author Matt Gacek
 * @see Score
 * @see ScoreDao
 */
@Database(entities = [Score::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scoreDao(): ScoreDao
}
