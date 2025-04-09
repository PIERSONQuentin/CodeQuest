package fr.unica.miage.pierson.codequest.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.unica.miage.pierson.codequest.viewmodel.QuizViewModel

/**
 * Écran de révision des réponses du quiz.
 *
 * @param viewModel ViewModel contenant les questions/réponses.
 * @param navController pour revenir en arrière si besoin.
 */
@Composable
fun QuizReviewScreen(
    viewModel: QuizViewModel,
    navController: NavController
) {
    val questions = viewModel.questions
    val userAnswers = viewModel.selectedAnswers

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        questions.forEachIndexed { index, question ->
            Column(modifier = Modifier.padding(vertical = 16.dp)) {
                Text(
                    text = "Question ${index + 1}",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = question.codeSnippet,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.padding(12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = question.questionText,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                question.options.forEachIndexed { optionIndex, optionText ->
                    val isCorrect = question.correctAnswersIndexes.contains(optionIndex)
                    val isSelected = userAnswers[index]?.contains(optionIndex) == true

                    val color = when {
                        isCorrect && isSelected -> Color(0xFF2E7D32) // ✅ vert
                        !isCorrect && isSelected -> Color(0xFFD32F2F) // ❌ rouge
                        isCorrect -> Color(0xFF81C784) // ✅ vert clair
                        else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f) // neutre
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .background(color.copy(alpha = 0.1f), RoundedCornerShape(6.dp))
                            .padding(12.dp)
                    ) {
                        Text(optionText, color = color)
                    }
                }
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate(fr.unica.miage.pierson.codequest.QuizListRoute) {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text("Retour à l'accueil")
        }
    }
}