package fr.unica.miage.pierson.codequest.data

/**
 * Source de données pour générer des séries de questions.
 *
 * Cette source est utilisée pour simuler des questions localement,
 * afin de tester l'application avant l'intégration de l'API (ChatGPT).
 */
object QuestionSource {

    /**
     * Retourne une série de questions associées à un quiz donné.
     *
     * @param idQuiz L'identifiant du quiz (correspond à sa position dans la liste).
     * @return Une liste de questions simulées pour ce quiz.
     */
    fun getQuestionsForQuiz(idQuiz: Int): List<Question> {
        return when (idQuiz) {
            0 -> listOf(
                Question(
                    codeSnippet = """
                        def say_hello():
                            print("Hello, world!")
                    """.trimIndent(),
                    questionText = "Que se passe-t-il si on exécute say_hello() ?",
                    options = listOf(
                        "Le texte 'Hello, world!' s'affiche.",
                        "Une erreur est levée.",
                        "Rien ne se passe.",
                        "La fonction retourne une valeur."
                    ),
                    correctAnswersIndexes = listOf(0)
                ),
                Question(
                    codeSnippet = """
                        x = [1, 2, 3]
                        y = x
                        x.append(4)
                        print(y)
                    """.trimIndent(),
                    questionText = "Quels éléments seront dans la liste y ?",
                    options = listOf(
                        "[1, 2, 3]",
                        "[1, 2, 3, 4]",
                        "[4]",
                        "Erreur de type"
                    ),
                    correctAnswersIndexes = listOf(1)
                )
            )

            1 -> listOf(
                Question(
                    codeSnippet = """
                        public class Test {
                            public static void main(String[] args) {
                                int x = 5;
                                System.out.println(x > 3 ? "Oui" : "Non");
                            }
                        }
                    """.trimIndent(),
                    questionText = "Que va afficher ce code Java ?",
                    options = listOf("Oui", "Non", "true", "Erreur"),
                    correctAnswersIndexes = listOf(0)
                )
            )

            2 -> listOf(
                Question(
                    codeSnippet = """
                        #include <iostream>
                        using namespace std;

                        int main() {
                            int x = 10;
                            if (x == 10)
                                cout << "C++";
                            return 0;
                        }
                    """.trimIndent(),
                    questionText = "Quels éléments sont vrais à propos de ce code ?",
                    options = listOf(
                        "Il affiche 'C++'.",
                        "Il retourne 1.",
                        "La condition est vraie.",
                        "Il contient une erreur de compilation."
                    ),
                    correctAnswersIndexes = listOf(0, 2)
                )
            )

            else -> emptyList()
        }
    }
}