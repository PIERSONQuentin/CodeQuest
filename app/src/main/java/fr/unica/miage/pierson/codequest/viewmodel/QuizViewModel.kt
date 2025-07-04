package fr.unica.miage.pierson.codequest.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.unica.miage.pierson.codequest.data.Question
import fr.unica.miage.pierson.codequest.data.QuestionSource
import kotlinx.coroutines.launch

/**
 * ViewModel pour gérer le déroulement d'un quiz.
 */
class QuizViewModel : ViewModel() {

    var questions by mutableStateOf<List<Question>>(emptyList())
        private set

    var currentQuestionIndex by mutableStateOf(0)
        private set

    // On utilise une Map observable de sets immuables (pour forcer recomposition)
    var selectedAnswers = mutableStateMapOf<Int, Set<Int>>()

    val currentQuestion: Question?
        get() = questions.getOrNull(currentQuestionIndex)

    var isLoading by mutableStateOf(false)
        private set

    /**
     * Charge les questions d'un quiz donné par son id.
     */
    fun loadQuestionsForQuiz(idQuiz: Int) {
        viewModelScope.launch {
            isLoading = true
            val loadedQuestions = QuestionSource.getQuestionsForQuiz(idQuiz)
            questions = loadedQuestions
            currentQuestionIndex = 0
            selectedAnswers.clear()
            isLoading = false
        }
    }

    /**
     * Active ou désactive un choix sélectionné pour la question actuelle.
     */
    fun toggleAnswer(index: Int) {
        val currentSet = selectedAnswers[currentQuestionIndex]?.toMutableSet() ?: mutableSetOf()
        if (currentSet.contains(index)) {
            currentSet.remove(index)
        } else {
            currentSet.add(index)
        }
        // Re-assigne pour forcer recomposition
        selectedAnswers[currentQuestionIndex] = currentSet.toSet()
    }

    /**
     * Passe à la question suivante.
     */
    fun goToNextQuestion() {
        if (currentQuestionIndex < questions.lastIndex) {
            currentQuestionIndex++
        }
    }

    fun isLastQuestion(): Boolean {
        return currentQuestionIndex == questions.lastIndex
    }

    /**
     * Calcule le score total.
     * Compare les indices cochés par l'utilisateur à la bonne réponse.
     */
    fun getScore(): Int {
        return questions.mapIndexed { index, question ->
            val userAnswers = selectedAnswers[index] ?: emptySet()
            val correctAnswers = question.correctAnswersIndexes.toSet()
            if (userAnswers == correctAnswers) 1 else 0
        }.sum()
    }

    fun getTotal(): Int = questions.size
}