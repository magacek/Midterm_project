package com.example.midterm_project

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class DeleteConfirmationDialog(
    private val score: Score,
    private val onConfirm: (Score) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity)
            .setMessage("Are you sure you want to delete this score?")
            .setPositiveButton("Delete") { _, _ ->
                onConfirm(score)
            }
            .setNegativeButton("Cancel", null)
            .create()
    }
}
