package fr.unica.miage.pierson.codequest.data

import fr.unica.miage.pierson.codequest.R

/**
 * DataSource class that provides a list of quizzes.
 *
 * This class contains a method to load a list of quizzes, each represented by a Quiz object.
 * The quizzes include various programming languages and concepts.
 */

class DataSource {
    fun loadQuizzes(): List<Quiz> {
        return listOf(
            Quiz(
                title = "Introduction à Python",
                description = "Ce quiz porte sur le langage Python et ses concepts de base.",
                note = 5,
                image = R.drawable.python,
                type = "QCM"
            ),
            Quiz(
                title = "Les fondamentaux de Java",
                description = "Testez vos connaissances sur les fondamentaux de Java, tels que la programmation orientée objet, l’héritage et la gestion des exceptions.",
                note = 4,
                image = R.drawable.java,
                type = "QCM"
            ),
            Quiz(
                title = "C++ pour débutants",
                description = "Un quiz dédié aux concepts essentiels de C++, couvrant la syntaxe et les bases de la STL.",
                note = 3,
                image = R.drawable.cpp,
                type = "QCM"
            ),
            Quiz(
                title = "Les bases des algorithmes",
                description = "Évaluez votre compréhension des algorithmes classiques tels que le tri, la recherche et d’autres concepts fondamentaux.",
                note = 5,
                image = R.drawable.algorithms,
                type = "QCM"
            ),
            Quiz(
                title = "Structures de données",
                description = "Un quiz sur les structures de données courantes : tableaux, listes, piles, files d’attente, et arbres.",
                note = 4,
                image = R.drawable.datastructures,
                type = "QCM"
            )
        )
    }

    fun loadQuiz(id: Int): Quiz {
        return loadQuizzes()[id]
    }
}