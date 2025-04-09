package fr.unica.miage.pierson.codequest.data

/**
 * Représente une question de quiz.
 *
 * @property codeSnippet Un extrait de code affiché avant la question.
 * @property questionText Le texte de la question.
 * @property options Les choix de réponse proposés.
 * @property correctAnswersIndexes L'indice de la bonne réponse dans la liste options.
 */

data class Question(
    val codeSnippet: String,
    val questionText: String,
    val options: List<String>,
    val correctAnswersIndexes: List<Int>
)