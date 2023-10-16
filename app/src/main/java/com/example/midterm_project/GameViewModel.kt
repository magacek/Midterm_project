package com.example.midterm_project

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
/**
 * `GameViewModel` is a ViewModel class responsible for managing and storing game-specific data.
 * This data includes the number of user attempts, the latest player's name, and the latest score.
 * The data is stored in LiveData objects to allow UI components to observe changes and update accordingly.
 *
 * <p>Functions provided allow the updating of attempts and setting the latest game-related information.
 * Typically, these methods would be called from fragments or activities that interact with the game
 * logic and require updating or reading the game state.</p>
 *
 * Example Usage:
 * <pre>
 * val viewModel: GameViewModel by activityViewModels()
 * viewModel.incrementAttempts()
 * </pre>
 *
 * @author Matt Gacek
 * @see androidx.lifecycle.ViewModel
 */

class GameViewModel : ViewModel() {
    val numberOfAttempts = MutableLiveData<Int>()
    val latestPlayerName = MutableLiveData<String?>()
    val latestScore = MutableLiveData<Int?>()

    fun incrementAttempts() {
        val current = numberOfAttempts.value ?: 0
        numberOfAttempts.value = current + 1
    }

    fun setLatestGameInfo(playerName: String, score: Int) {
        latestPlayerName.value = playerName
        latestScore.value = score
    }
}
