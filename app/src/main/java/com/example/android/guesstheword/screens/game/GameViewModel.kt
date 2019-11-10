package com.example.android.guesstheword.screens.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    // The current word
    private val _word = MutableLiveData<String>() // Writable
    val word: LiveData<String> // Readable - encapsulated
        get() = _word // Getter

    // The current score
    private val _score = MutableLiveData<Int>() // Writable
    val score: LiveData<Int> // Readable - encapsulated
        get() = _score // Getter

    // GameFinished LiveData
    private val _eventGameFinished = MutableLiveData<Boolean>()
    val eventGameFinished: LiveData<Boolean>
        get() = _eventGameFinished


    /**
     * Logic işlemler ViewModel içerisinde yapılıyor.
     * Refresh işlemleri için 2 taraftada yapılıyor.
     */

    init {
        Log.i("GameViewModel", "GameViewModel Created")
        resetList()
        nextWord()

        // İlklendirildi.
        _word.value = "" // Play ' e tıklandığında ilk kelime gelmeyecektir.
        _score.value = 0
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel Destoyed")
    }


    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    /**
     * Resets the list of words and randomizes the order
     */
    fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }


    /**
     * Moves to the next word in the list
     */
    fun nextWord() {
        if (!wordList.isEmpty()) {
            //Select and remove a word from the list
            _word.value = wordList.removeAt(0)
        } else if (wordList.isEmpty()) {
            onGameFinished()
        }

    }


    /** Methods for buttons presses **/

    fun onSkip() {
        if (!wordList.isEmpty()) {
            _score.value = (score.value)?.minus(1)
        }
        nextWord()
    }

    fun onCorrect() {
        if (!wordList.isEmpty()) {
            _score.value = (score.value)?.plus(1)
        }
        nextWord()
    }

    fun onGameFinished() {
        _eventGameFinished.value = true
    }

    fun onGameFinishComplete() {
        _eventGameFinished.value = false
    }

}