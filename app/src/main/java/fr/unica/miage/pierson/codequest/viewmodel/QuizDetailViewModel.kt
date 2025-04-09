package fr.unica.miage.pierson.codequest.viewmodel

import androidx.lifecycle.ViewModel
import fr.unica.miage.pierson.codequest.data.DataSource
import fr.unica.miage.pierson.codequest.data.Quiz

/**
 * ViewModel pour l'écran de détails d'un quiz.
 *
 * Ce ViewModel est responsable de fournir les données d'un quiz à afficher.
 * Il utilise une source de données locale (DataSource) pour la récupération.
 *
 * @property quiz Le quiz sélectionné à afficher.
 */

class QuizDetailViewModel : ViewModel() {

    var quiz: Quiz? = null
        private set

    /**
     * Charge les données du quiz en fonction de son identifiant.
     *
     * @param id L'identifiant du quiz à charger.
     */
    fun loadQuizById(id: Int) {
        quiz = DataSource().loadQuiz(id)
    }
}