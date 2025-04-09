package fr.unica.miage.pierson.codequest.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fr.unica.miage.pierson.codequest.viewmodel.QuizDetailViewModel

/**
 * Écran de détails d'un quiz.
 *
 * Cet écran affiche les informations principales d'un quiz (image, titre, note, description),
 * ainsi qu'un bouton permettant de démarrer le quiz.
 *
 * @param idQuiz L'identifiant du quiz à afficher.
 * @param viewModel Le ViewModel associé à l'écran.
 * @param onStartQuiz Callback déclenché lorsque l'utilisateur souhaite commencer le quiz.
 */

@Composable
fun QuizDetailScreen(
    idQuiz: Int,
    viewModel: QuizDetailViewModel,
    onStartQuiz: () -> Unit
) {
    viewModel.loadQuizById(idQuiz)
    val quiz = viewModel.quiz

    if (quiz == null) {
        Text("Chargement du quiz...", modifier = Modifier.padding(16.dp))
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = quiz.image),
                    contentDescription = "Image du quiz",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = quiz.title,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    repeat(5) { index ->
                        val filled = index < quiz.note
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = if (filled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = quiz.description,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.weight(1f))
            }

            // Bouton fixé en bas
            Button(
                onClick = onStartQuiz,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text("Commencer le quiz")
            }
        }
    }
}