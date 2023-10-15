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
                // TODO: Update RecyclerView with scores
            }
        }

        viewModel.numberOfAttempts.observe(viewLifecycleOwner) { count ->
            view.findViewById<TextView>(R.id.attemptsTextView).text = "Number of attempts: $count"
        }
    }
}
