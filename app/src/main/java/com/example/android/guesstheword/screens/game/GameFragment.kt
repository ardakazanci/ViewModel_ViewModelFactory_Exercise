/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.guesstheword.screens.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.GameFragmentBinding

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {

    /**
     * GameViewModel
     */
    private lateinit var viewModel: GameViewModel

    /**
     * UI Referanslarını tutuyor.
     */
    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        Log.i("GameFragment", "Called ViewModelProviders.of")
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)

        /**
         * Observer ViewModel Data Value
         * Skor veya kelimenin değeri değiştiğinde, ekranda görüntülenen skor veya kelime şimdi otomatik olarak güncellenir.
         * updateScoreText() gerek yok
         * updateWordText() gerek yok
         */

        viewModel.score.observe(this, Observer {
            binding.scoreText.text = it.toString()
        })

        viewModel.word.observe(this, Observer {
            binding.wordText.text = it
        })

        viewModel.eventGameFinished.observe(this, Observer {
            if (it) onGameFinished()
        })


        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.game_fragment,
                container,
                false
        )



        binding.correctButton.setOnClickListener { onCorrect() }
        binding.skipButton.setOnClickListener { onSkip() }
        binding.endGameButton.setOnClickListener { onEndGame() }

        return binding.root

    }


    /** Methods for buttons presses **/

    /**
     * Soru Atlandı.
     */
    private fun onSkip() {
        // ViewModel içerisinde ki score puanı ayarlandı
        viewModel.onSkip()
    }

    /**
     * Soru Doğru Yanıtlandı
     */
    private fun onCorrect() {
        // ViewModel içerisinde ki score puanı ayarlandı
        viewModel.onCorrect()
    }


    /** Methods for updating the UI **/

    private fun onEndGame() {
        onGameFinished()
    }

    private fun onGameFinished() {
        Toast.makeText(activity, "Game has just finished", Toast.LENGTH_SHORT).show()
        // Directions belirle.
        val action = GameFragmentDirections.actionGameToScore()
        // Şu değere ata
        action.score = viewModel.score.value ?: 0
        // Şuraya git.
        // UI Davranışı. Logic yok.
        NavHostFragment.findNavController(this).navigate(action)

        /**
         * Eğer eklenmez ise, Yapılandırma durumlarında her defaısnda tekrar tekrar Toast Mesajı görüntülenir.
         * Çünkü tekrar işleme konulur.
         */
        viewModel.onGameFinishComplete()

    }


}
