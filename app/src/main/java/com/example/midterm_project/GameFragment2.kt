package com.example.midterm_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
/**
 * Represents a fragment (`GameFragment2`) that displays game statistics and information to the user.
 *
 * <p>The fragment layout (`fragment_game_2`) contains views to present the user's score,
 * number of attempts, and potentially other game metrics. The fragment fetches scores from
 * the database and listens to changes in the shared `GameViewModel` to reflect the user's
 * latest game attempts in real-time.</p>
 *
 * <p>The logic within this fragment utilizes Kotlin coroutines to ensure that database operations
 * are performed off the main thread, providing smoother UI interactions.</p>
 *
 * @author Matt Gacek
 * @see GameViewModel
 * @see MainActivity
 */
class GameFragment2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game_2, container, false)
    }
    fun updateAttempts(count: Int) {
        view?.findViewById<TextView>(R.id.attemptsTextView)?.text = "Number of attempts: $count"
    }


    private val viewModel: GameViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch(Dispatchers.IO) {
            val scores = MainActivity.db.scoreDao().getAllScores()
            withContext(Dispatchers.Main) {
            }
        }

        viewModel.numberOfAttempts.observe(viewLifecycleOwner) { count ->
            view.findViewById<TextView>(R.id.attemptsTextView).text = "Number of attempts: $count"
        }
    }
}
