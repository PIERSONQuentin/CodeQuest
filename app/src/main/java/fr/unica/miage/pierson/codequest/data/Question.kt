package fr.unica.miage.pierson.codequest.data

/**
 * Data class représentant une question de quiz.
 *
 * @property questionText Le texte de la question.
 * @property options La liste des options de réponse.
 * @property correctAnswerIndex L'index de la réponse correcte dans la liste des options.
 */
data class Question(
    val questionText: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)
