package fr.unica.miage.pierson.codequest.data

/**
 * Data class representing a quiz.
 *
 * @property title The title of the quiz.
 * @property description A brief description of the quiz.
 * @property note The note associated with the quiz.
 * @property image The resource ID of the image associated with the quiz.
 * @property type The type of the quiz (e.g., "QCM", "CLOZE", etc.).
 */

// A Quiz can have two types: QCM (multiple choice) or CLOZE (fill in the blanks).
data class Quiz(
    val title: String,
    val description: String,
    val note: Int,
    val image: Int,
    val type: String
)
