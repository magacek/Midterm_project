package com.example.midterm_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * Represents a fragment (`GameFragment`) that serves as a container for the game UI.
 *
 * <p>This fragment, upon creation, inflates the `fragment_game` layout and embeds two child fragments
 * (`GameFragment1` and `GameFragment2`) within specified container views in the layout.</p>
 *
 * <p>To embed this fragment into an activity or another fragment, typically a fragment transaction
 * would be used. For example:</p>
 * <pre>
 * supportFragmentManager.beginTransaction()
 *     .replace(R.id.someContainer, GameFragment())
 *     .commit()
 * </pre>
 *
 * @author Matt Gacek
 * @see GameFragment1
 * @see GameFragment2
 */
class GameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment1Container, GameFragment1())
            .replace(R.id.fragment2Container, GameFragment2())
            .commit()

    }
}
