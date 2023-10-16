package com.example.midterm_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
/**
 * Represents a fragment (`HighScoreFragment`) that showcases a list of high scores to the user.
 *
 * <p>The fragment layout (`fragment_highscore`) provides a RecyclerView to display the scores
 * and a TextView to inform the user when there are no scores available. The high scores are fetched
 * from the database and shown in the RecyclerView using the `ScoreAdapter`. If a score is selected,
 * a `DeleteConfirmationDialog` prompts the user for confirmation before deleting the score.</p>
 *
 * <p>Kotlin coroutines are utilized to perform database operations off the main thread, ensuring
 * smooth UI interactions.</p>
 *
 * @author Matt Gacek
 * @see ScoreAdapter
 * @see DeleteConfirmationDialog
 * @see MainActivity
 */
class HighScoreFragment : Fragment() {
    private lateinit var adapter: ScoreAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var noScoresTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_highscore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.highScoresRecyclerView)
        noScoresTextView = view.findViewById(R.id.noScoresTextView)

        // Set the RecyclerView's LayoutManager
        recyclerView.layoutManager = LinearLayoutManager(context)

        fetchScoresAndSetupAdapter()
    }

    private fun fetchScoresAndSetupAdapter() {
        GlobalScope.launch(Dispatchers.IO) {
            val scores = MainActivity.db.scoreDao().getAllScores()
            withContext(Dispatchers.Main) {
                if (scores.isEmpty()) {
                    noScoresTextView.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                } else {
                    noScoresTextView.visibility = View.GONE
                    adapter = ScoreAdapter(scores) { score ->
                        showDeleteConfirmationDialog(score)
                    }
                    recyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }
        }
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
            fetchScoresAndSetupAdapter()  // Refresh the list after deletion
        }
    }
}
