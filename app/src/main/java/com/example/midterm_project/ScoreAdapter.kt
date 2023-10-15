package com.example.midterm_project

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.midterm_project.databinding.ItemHighscoreBinding

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
