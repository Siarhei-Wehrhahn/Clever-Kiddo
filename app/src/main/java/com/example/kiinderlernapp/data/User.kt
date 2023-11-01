package com.example.kiinderlernapp.data

import com.example.kiinderlernapp.R
import com.example.kiinderlernapp.data.datamodels.Quiz


object Questions {

    val questions: List<Quiz>
        get() {
            return loadQuestions()
        }

    private fun loadQuestions(): List<Quiz> {
        var pics = mutableListOf(
            R.drawable.apfel,
            R.drawable.blumenkohl,
            R.drawable.broccoli,
            R.drawable.erbsen,
            R.drawable.erdbeere,
            R.drawable.granatapfel,
            R.drawable.gurcke,
            R.drawable.kiwi,
            R.drawable.kopfsalat,
            R.drawable.mais,
            R.drawable.radieschen,
            R.drawable.rosenkohl,
            R.drawable.tomate,
            R.drawable.weintrauben,
            R.drawable.zwiebel
        )
        return pics.map { rightAnswere ->
            // Erstelle eine Kopie der pics-Liste ohne das richtige Antwortbild
            val options = pics.toMutableList()
            options.remove(rightAnswere)

            // Mische die options-Liste zufällig
            options.shuffle()

            return listOf(
                Quiz(
                    question = "Tippe auf den Brokkoli!",
                    R.drawable.broccoli,
                    options[0],
                    options[1],
                    options[2],
                    1
                ),

                Quiz(
                    question = "Wähle die Erdbeere aus!",
                    options[3],
                    R.drawable.erdbeere,
                    options[4],
                    options[5],
                    2
                ),

                Quiz(
                    question = "Tippe auf den Rosenkohl!",
                    R.drawable.rosenkohl,
                    options[6],
                    options[7],
                    options[8],
                    1
                ),

                Quiz(
                    question = "Berühre die Tomate!",
                    options[9],
                    options[10],
                    options[11],
                    R.drawable.tomate,
                    4
                ),

                Quiz(
                    question = "Wähle die Radieschen aus!",
                    options[12],
                    options[13],
                    R.drawable.radieschen,
                    options[1],
                    3
                ),

                Quiz(
                    question = "Berühre die Erbsen!",
                    options[0],
                    R.drawable.erbsen,
                    options[1],
                    options[3],
                    2
                ),

                Quiz(
                    question = "Klicke auf den Granatapfel!",
                    options[0],
                    options[3],
                    R.drawable.granatapfel,
                    options[2],
                    3
                ),

                Quiz(
                    question = "Tippe auf den Apfel!",
                    R.drawable.apfel,
                    options[5],
                    options[6],
                    options[7],
                    1
                ),

                Quiz(
                    question = "Drücke auf den Mais!",
                    options[1],
                    options[2],
                    options[3],
                    R.drawable.mais,
                    4
                ),

                Quiz(
                    question = "Klicke auf die Zwiebeln!",
                    options[0],
                    options[1],
                    R.drawable.zwiebel,
                    options[3],
                    3
                ),

                Quiz(
                    question = "Berühre die Weintrauben!",
                    R.drawable.weintrauben,
                    options[1],
                    options[2],
                    options[3],
                    1
                ),

                Quiz(
                    question = "Wähle die Gurke!",
                    options[1],
                    options[2],
                    R.drawable.gurcke,
                    options[3],
                    3
                ),

                Quiz(
                    question = "Drücke auf den Kopfsalat!",
                    options[6],
                    options[7],
                    options[8],
                    R.drawable.kopfsalat,
                    4
                ),

                Quiz(
                    question = "Tippe auf die Blumenkohl!",
                    R.drawable.blumenkohl,
                    options[3],
                    options[2],
                    options[4],
                    1
                ),

                Quiz(
                    question = "Wähle die Kiwi!",
                    options[8],
                    options[9],
                    R.drawable.kiwi,
                    options[1],
                    3
                )
            )
        }
    }
}