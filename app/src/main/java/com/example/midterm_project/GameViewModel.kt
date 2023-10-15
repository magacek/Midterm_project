package com.example.midterm_project

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    val numberOfAttempts = MutableLiveData<Int>()

    fun incrementAttempts() {
        val current = numberOfAttempts.value ?: 0
        numberOfAttempts.value = current + 1
    }
}
