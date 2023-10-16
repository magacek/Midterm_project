package com.example.midterm_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

/**
 * Represents the primary fragment (`MainFragment`) that acts as the main menu or dashboard for the application.
 *
 * <p>The fragment layout (`fragment_main`) provides a welcoming message displaying the latest player's score
 * and offers two main actions to the user: starting a new game and viewing high scores.</p>
 *
 * <p>The fragment leverages a shared `GameViewModel` to retrieve and observe the latest game data,
 * ensuring that the welcoming message updates in real-time as games are played.</p>
 *
 * @author Matt Gacek
 * @see GameViewModel
 * @see findNavController
 */
class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: GameViewModel by activityViewModels()

        viewModel.latestPlayerName.observe(viewLifecycleOwner) { name ->
            viewModel.latestScore.observe(viewLifecycleOwner) { score ->
                if (!name.isNullOrEmpty() && score != null) {
                    view.findViewById<TextView>(R.id.welcomeTextView).text = "$name's score: $score\nPlay another game?"
                }
            }
        }

        view.findViewById<Button>(R.id.playGameButton).setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_gameFragment)
        }

        view.findViewById<Button>(R.id.viewHighScoresButton).setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_highScoreFragment)
        }
    }

}
