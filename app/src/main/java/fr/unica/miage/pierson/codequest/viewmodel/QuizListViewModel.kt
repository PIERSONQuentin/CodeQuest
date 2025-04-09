package fr.unica.miage.pierson.codequest.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import fr.unica.miage.pierson.codequest.data.DataSource
import fr.unica.miage.pierson.codequest.data.Quiz

/**
 * ViewModel pour gérer l'état de la liste des quiz.
 *
 * Cette classe est responsable de la gestion de la logique métier et de l'état de la liste des quiz.
 * Elle utilise un DataSource pour charger les quiz
 * et fournit une méthode pour filtrer les quiz en fonction d'une requête de recherche.
 */

class QuizListViewModel : ViewModel() {

    private val allQuizzes = DataSource().loadQuizzes()

    var searchQuery by mutableStateOf("")
        private set

    val filteredQuizzes: List<Quiz>
        get() = if (searchQuery.isBlank()) {
            allQuizzes
        } else {
            allQuizzes.filter {
                it.title.contains(searchQuery, ignoreCase = true)
            }
        }

    fun onSearchChange(query: String) {
        searchQuery = query
    }
}