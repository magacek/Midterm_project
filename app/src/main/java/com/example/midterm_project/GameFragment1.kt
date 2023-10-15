package com.example.midterm_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import java.util.Random
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.navigation.fragment.findNavController

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
        view.findViewById<Button>(R.id.decrementButton).setOnClickListener {
            val currentValue = view.findViewById<EditText>(R.id.guessNumberEditText).text.toString().toIntOrNull() ?: 0
            if (currentValue > 0) {
                view.findViewById<EditText>(R.id.guessNumberEditText).setText((currentValue - 1).toString())
            }
        }

        // Plus button logic
        view.findViewById<Button>(R.id.incrementButton).setOnClickListener {
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

    private fun checkGuess(guess: Int) {
        viewModel.incrementAttempts()

        // Debugging Toast to check the random number

        if (guess > randomNumber) {
            Toast.makeText(context, "Guess is lower!", Toast.LENGTH_SHORT).show()
        } else if (guess < randomNumber) {
            Toast.makeText(context, "Guess is higher!", Toast.LENGTH_SHORT).show()
        } else {
            // Correct guess
            val playerName = view?.findViewById<EditText>(R.id.playerNameEditText)?.text.toString()
            val attempts = viewModel.numberOfAttempts.value ?: 0
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
