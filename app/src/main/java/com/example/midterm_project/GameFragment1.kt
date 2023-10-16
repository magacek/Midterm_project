package com.example.midterm_project

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import java.util.Random
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.navigation.fragment.findNavController
/**
 * Represents the main game interface for guessing a randomly generated number between 1 and 100.
 *
 * <p>The fragment layout (`fragment_game_1`) includes functionality to increment and decrement
 * the user's guess using dedicated buttons, an EditText for entering guesses, and a button to
 * submit a guess. On a correct guess, the user's information is stored in the database and
 * navigation proceeds to the `mainFragment`. If the guess is incorrect, appropriate feedback
 * is given to the user through toasts and a sound effect.</p>
 *
 * <p>The fragment leverages a shared `GameViewModel` to keep track of the game state and
 * interact with the main activity and other fragments.</p>
 *
 * @author Matt Gacek
 * @see GameViewModel
 * @see MainActivity
 */
class GameFragment1 : Fragment() {
    private var randomNumber = 0
    private val viewModel: GameViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        randomNumber = Random().nextInt(100) + 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game_1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Minus button logic
        view.findViewById<ImageButton>(R.id.decrementButton).setOnClickListener {
            val currentValue = view.findViewById<EditText>(R.id.guessNumberEditText).text.toString().toIntOrNull() ?: 0
            if (currentValue > 0) {
                view.findViewById<EditText>(R.id.guessNumberEditText).setText((currentValue - 1).toString())
            }
        }

        // Plus button logic
        view.findViewById<ImageButton>(R.id.incrementButton).setOnClickListener {
            val currentValue = view.findViewById<EditText>(R.id.guessNumberEditText).text.toString().toIntOrNull() ?: 0
            view.findViewById<EditText>(R.id.guessNumberEditText).setText((currentValue + 1).toString())
        }

        // OK button logic
        view.findViewById<Button>(R.id.submitGuessButton).setOnClickListener {
            val guessString = view.findViewById<EditText>(R.id.guessNumberEditText).text.toString()
            val guess = guessString.toIntOrNull()
            if (guess != null) {
                checkGuess(guess)
            } else {
                Toast.makeText(context, "Please enter a valid number", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun playIncorrectAnswerSound() {
        val mediaPlayer = MediaPlayer.create(context, R.raw.wrong_sound)
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener { it.release() }
    }

    private fun checkGuess(guess: Int) {
        viewModel.incrementAttempts()


        if (guess > randomNumber) {
            Toast.makeText(context, "Guess is lower!", Toast.LENGTH_SHORT).show()
            playIncorrectAnswerSound()
        } else if (guess < randomNumber) {
            Toast.makeText(context, "Guess is higher!", Toast.LENGTH_SHORT).show()
            playIncorrectAnswerSound()
        } else {
            val playerName = view?.findViewById<EditText>(R.id.playerNameEditText)?.text.toString()
            val attempts = viewModel.numberOfAttempts.value ?: 0

            viewModel.setLatestGameInfo(playerName, attempts)

            val score = Score(playerName = playerName, attempts = attempts)

            GlobalScope.launch(Dispatchers.IO) {
                MainActivity.db.scoreDao().insert(score)
                withContext(Dispatchers.Main) {
                    findNavController().navigate(R.id.action_gameFragment_to_mainFragment)
                }
            }
        }
    }
}
