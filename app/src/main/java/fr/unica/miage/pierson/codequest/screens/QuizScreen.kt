package fr.unica.miage.pierson.codequest.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import fr.unica.miage.pierson.codequest.viewmodel.QuizViewModel

/**
 * Écran de jeu du quiz.
 *
 * Affiche un extrait de code, une question, des choix de réponses et un bouton pour naviguer.
 *
 * @param idQuiz L'identifiant du quiz.
 * @param viewModel Le ViewModel du quiz.
 * @param onFinishQuiz Callback lorsque le quiz est terminé.
 */
@Composable
fun QuizScreen(
    idQuiz: Int,
    viewModel: QuizViewModel,
    onFinishQuiz: () -> Unit
) {
    LaunchedEffect(idQuiz) {
        viewModel.loadQuestionsForQuiz(idQuiz)
    }

    val question = viewModel.currentQuestion ?: return

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Indicateur de progression
            Text(
                text = "Question ${viewModel.currentQuestionIndex + 1}/${viewModel.getTotal()}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Bloc d'extrait de code
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = question.codeSnippet,
                        fontFamily = FontFamily.Monospace,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Texte de la question
            Text(
                text = question.questionText,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Choix de réponses
            Column {
                question.options.forEachIndexed { index, option ->
                    val isSelected = viewModel.selectedAnswers[viewModel.currentQuestionIndex]?.contains(index) == true
                    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                    else MaterialTheme.colorScheme.surfaceVariant

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .background(backgroundColor, RoundedCornerShape(8.dp))
                            .clickable { viewModel.toggleAnswer(index) }
                            .padding(12.dp)
                    ) {
                        Text(option)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Bouton Suivant / Voir le score
            Button(
                onClick = {
                    if (viewModel.isLastQuestion()) onFinishQuiz()
                    else viewModel.goToNextQuestion()
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
            ) {
                Text(if (viewModel.isLastQuestion()) "Voir le score" else "Suivant")
            }
        }
    }
}