package com.example.midterm_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HighScoreFragment : Fragment() {
    private lateinit var adapter: ScoreAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_highscore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch(Dispatchers.IO) {
            val scores = MainActivity.db.scoreDao().getAllScores()
            withContext(Dispatchers.Main) {
                if (scores.isEmpty()) {
                    view.findViewById<TextView>(R.id.noScoresTextView).visibility = View.VISIBLE
                } else {
                    adapter = ScoreAdapter(scores) { score ->
                        showDeleteConfirmationDialog(score)
                    }
                    recyclerView.adapter = adapter
                }
            }
        }

        // TODO: Any other logic you want in this method
    }
    private fun showDeleteConfirmationDialog(score: Score) {
        val dialog = DeleteConfirmationDialog(score) { confirmedScore ->
            deleteScore(confirmedScore)
        }
        dialog.show(childFragmentManager, "DeleteConfirmation")
    }

    private fun deleteScore(score: Score) {
        GlobalScope.launch(Dispatchers.IO) {
            MainActivity.db.scoreDao().delete(score.id)
            // Refresh the list after deletion
            val scores = MainActivity.db.scoreDao().getAllScores()
            withContext(Dispatchers.Main) {
                adapter = ScoreAdapter(scores) { scoreToBeDeleted ->
                    showDeleteConfirmationDialog(scoreToBeDeleted)
                }
                recyclerView.adapter = adapter
            }
        }
    }

}
