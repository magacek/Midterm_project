package com.example.midterm_project

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.midterm_project.databinding.ItemHighscoreBinding

/**
 * Adapter for displaying a list of game scores in a RecyclerView.
 *
 * <p>This adapter uses data binding to bind `Score` objects to `ItemHighscoreBinding` layouts,
 * efficiently displaying each score in a list. Each item in the list displays the player's name,
 * their score (number of attempts), and a button to delete the score. When the delete button is clicked,
 * a provided callback (`onDeleteClick`) is invoked, allowing the containing component to handle the deletion.</p>
 *
 * Example Usage:
 * <pre>
 * val scores = listOf(Score(playerName = "John", attempts = 5), Score(playerName = "Jane", attempts = 7))
 * val adapter = ScoreAdapter(scores) { scoreToDelete ->
 *     // Handle score deletion
 * }
 * recyclerView.adapter = adapter
 * </pre>
 *
 * @property scores The list of scores to display in the RecyclerView.
 * @property onDeleteClick Callback for handling score deletion when the delete button of a score item is clicked.
 *
 * @author Matt Gacek
 * @see RecyclerView.Adapter
 * @see com.example.midterm_project.databinding.ItemHighscoreBinding
 */

class ScoreAdapter(
    private val scores: List<Score>,
    private val onDeleteClick: (Score) -> Unit
) : RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>() {

    inner class ScoreViewHolder(private val binding: ItemHighscoreBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(score: Score) {
            binding.playerNameTextView.text = score.playerName
            binding.scoreTextView.text = "Score: ${score.attempts}"
            binding.deleteScoreButton.setOnClickListener {
                onDeleteClick(score)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val binding = ItemHighscoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScoreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        holder.bind(scores[position])
    }

    override fun getItemCount(): Int = scores.size
}
