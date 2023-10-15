package com.example.midterm_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.playGameButton).setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_gameFragment)
        }

        view.findViewById<Button>(R.id.viewHighScoresButton).setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_highScoreFragment)
        }
    }
}
