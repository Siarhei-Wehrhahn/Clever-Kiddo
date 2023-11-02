package com.example.kiinderlernapp.data

import com.example.kiinderlernapp.R
import com.example.kiinderlernapp.data.datamodels.Quiz


object Questions {

    val questions: List<Quiz>
        get() {
            return loadQuestions()
        }

    // Funktion zum Laden von Fragen und Bildern für das Quiz
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

        return listOf(
            Quiz(
                question = "Tippe auf den Brokkoli!",
                R.drawable.broccoli,
                pics.filter { it != R.drawable.broccoli }.random(),
                pics.filter { it != R.drawable.broccoli }.random(),
                pics.filter { it != R.drawable.broccoli }.random(),
                1
            ),

            Quiz(
                question = "Wähle die Erdbeere aus!",
                pics.filter { it != R.drawable.erdbeere }.random(),
                R.drawable.erdbeere,
                pics.filter { it != R.drawable.erdbeere }.random(),
                pics.filter { it != R.drawable.erdbeere }.random(),
                2
            ),

            Quiz(
                question = "Tippe auf den Rosenkohl!",
                R.drawable.rosenkohl,
                pics.filter { it != R.drawable.rosenkohl }.random(),
                pics.filter { it != R.drawable.rosenkohl }.random(),
                pics.filter { it != R.drawable.rosenkohl }.random(),
                1
            ),

            Quiz(
                question = "Berühre die Tomate!",
                pics.filter { it != R.drawable.tomate }.random(),
                pics.filter { it != R.drawable.tomate }.random(),
                pics.filter { it != R.drawable.tomate }.random(),
                R.drawable.tomate,
                4
            ),

            Quiz(
                question = "Wähle die Radieschen aus!",
                pics.filter { it != R.drawable.radieschen }.random(),
                pics.filter { it != R.drawable.radieschen }.random(),
                R.drawable.radieschen,
                pics.filter { it != R.drawable.radieschen }.random(),
                3
            ),

            Quiz(
                question = "Berühre die Erbsen!",
                pics.filter { it != R.drawable.erbsen }.random(),
                R.drawable.erbsen,
                pics.filter { it != R.drawable.erbsen }.random(),
                pics.filter { it != R.drawable.erbsen }.random(),
                2
            ),

            Quiz(
                question = "Klicke auf den Granatapfel!",
                pics.filter { it != R.drawable.granatapfel }.random(),
                pics.filter { it != R.drawable.granatapfel }.random(),
                R.drawable.granatapfel,
                pics.filter { it != R.drawable.granatapfel }.random(),
                3
            ),

            Quiz(
                question = "Tippe auf den Apfel!",
                R.drawable.apfel,
                pics.filter { it != R.drawable.apfel }.random(),
                pics.filter { it != R.drawable.apfel }.random(),
                pics.filter { it != R.drawable.apfel }.random(),
                1
            ),

            Quiz(
                question = "Drücke auf den Mais!",
                pics.filter { it != R.drawable.mais }.random(),
                pics.filter { it != R.drawable.mais }.random(),
                pics.filter { it != R.drawable.mais }.random(),
                R.drawable.mais,
                4
            ),

            Quiz(
                question = "Klicke auf die Zwiebeln!",
                pics.filter { it != R.drawable.zwiebel }.random(),
                pics.filter { it != R.drawable.zwiebel }.random(),
                R.drawable.zwiebel,
                pics.filter { it != R.drawable.zwiebel }.random(),
                3
            ),

            Quiz(
                question = "Berühre die Weintrauben!",
                R.drawable.weintrauben,
                pics.filter { it != R.drawable.weintrauben }.random(),
                pics.filter { it != R.drawable.weintrauben }.random(),
                pics.filter { it != R.drawable.weintrauben }.random(),
                1
            ),

            Quiz(
                question = "Wähle die Gurke!",
                pics.filter { it != R.drawable.gurcke }.random(),
                pics.filter { it != R.drawable.gurcke }.random(),
                R.drawable.gurcke,
                pics.filter { it != R.drawable.gurcke }.random(),
                3
            ),

            Quiz(
                question = "Drücke auf den Kopfsalat!",
                pics.filter { it != R.drawable.kopfsalat }.random(),
                pics.filter { it != R.drawable.kopfsalat }.random(),
                pics.filter { it != R.drawable.kopfsalat }.random(),
                R.drawable.kopfsalat,
                4
            ),

            Quiz(
                question = "Tippe auf die Blumenkohl!",
                R.drawable.blumenkohl,
                pics.filter { it != R.drawable.blumenkohl }.random(),
                pics.filter { it != R.drawable.blumenkohl }.random(),
                pics.filter { it != R.drawable.blumenkohl }.random(),
                1
            ),

            Quiz(
                question = "Wähle die Kiwi!",
                pics.filter { it != R.drawable.kiwi }.random(),
                pics.filter { it != R.drawable.kiwi }.random(),
                R.drawable.kiwi,
                pics.filter { it != R.drawable.kiwi }.random(),
                3
            )
        )
    }
}
