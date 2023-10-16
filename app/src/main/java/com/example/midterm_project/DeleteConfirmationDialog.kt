package com.example.midterm_project

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

/**
 * `DeleteConfirmationDialog` is a DialogFragment used to prompt the user for confirmation
 * when attempting to delete a specific score. It provides options to either confirm the deletion
 * or cancel the operation.
 *
 * <p>When the user confirms deletion, the provided callback `onConfirm` is invoked with the score
 * to be deleted as an argument.</p>
 *
 * Usage:
 * <pre>
 * DeleteConfirmationDialog(score, { scoreToDelete ->
 *     // Handle the deletion logic here
 * }).show(fragmentManager, "deleteConfirmation")
 * </pre>
 *
 * @author Matt Gacek
 * @param score The score to potentially delete.
 * @param onConfirm Callback to handle confirmed deletion.
 */

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
