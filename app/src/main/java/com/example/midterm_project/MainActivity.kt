package com.example.midterm_project

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.room.Room
import com.example.midterm_project.AppDatabase
/**
 * The main activity of the application that serves as the primary entry point and navigation controller.
 *
 * <p>The activity layout (`activity_main`) contains a `NavHostFragment` which manages the navigation between
 * various fragments. It also initializes the app's Room database (`AppDatabase`) for persistent data storage
 * related to scores.</p>
 *
 * <p>The `MainActivity` offers a companion object with the initialized database instance, making it
 * conveniently accessible to other components of the app.</p>
 *
 * @author Matt Gacek
 * @see AppDatabase
 * @see NavHostFragment
 */

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    companion object {
        lateinit var db: AppDatabase
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the database here
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "scores-database"
        ).build()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }


}
